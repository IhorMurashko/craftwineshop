package com.craftWine.shop.service.wineRateServices;

import com.craftWine.shop.exceptions.EmailProblemException;
import com.craftWine.shop.exceptions.UserNotFoundException;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.User;
import com.craftWine.shop.models.WineEvaluation;
import com.craftWine.shop.repositories.WineEvaluationRepository;
import com.craftWine.shop.security.TokenProvider;
import com.craftWine.shop.service.crafWineServices.CraftWineService;
import com.craftWine.shop.service.userServices.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WineEvaluationServiceImpl implements WineEvaluationService {

    private final UserService userService;
    private final CraftWineService craftWineService;
    private final WineEvaluationRepository wineEvaluationRepository;
    private final TokenProvider tokenProvider;


    /**
     * Adds a new user grade for a craft wine and calculates the average grade for the wine.
     * <p>
     * This method takes a unique identifier {@code userEmail} for a user, a unique identifier {@code craftWineId} for a craft wine,
     * and the {@code evaluation} given by the user for the craft wine.
     * </p>
     * <p>
     * The method saves the rating into the database for the user. If this is the user's first rating for the wine, it is saved as a new entry,
     * otherwise, it updates the existing rating from the user for the wine.
     * </p>
     * <p>
     * The method also calculates a new average grade for the wine and updates it in the database for the wine.
     * </p>
     *
     * @param token       The user token from Authorization header
     * @param craftWineId The unique identifier of the wine in the database.
     * @param rate        The grade given by the user for the wine.
     * @return {@code true} if the update was successful.
     * @throws EmailProblemException if user with email {@code userEmail} not found
     */


    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public boolean addRateForTheWine(String token, long craftWineId, short rate) {

        String userEmail = tokenProvider.extractUsername(token);


        // get user by user's email
        Optional<User> optionalUser = userService.findUserByEmail(userEmail);

        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new EmailProblemException("Could not find user with email " + userEmail);
        }
        //get craft wine by craft wine's id;
        Optional<CraftWine> craftWineOptional = craftWineService.findById(craftWineId);

        CraftWine craftWine = craftWineOptional.orElseThrow(() -> new UserNotFoundException("Could not find craft with id " + craftWineId));


        //looking for a grade by the user for the wine
        Optional<Long> existStarForTheWineByUser = wineEvaluationRepository.isExistStarForTheWineByUser(
                user, craftWine);

        WineEvaluation wineEvaluation;

        //if user has already added grade for this wine
        if (existStarForTheWineByUser.isPresent() && existStarForTheWineByUser.get() > 0) {
            //update a grade by user for the wine
            Long star = existStarForTheWineByUser.get();
            wineEvaluation = new WineEvaluation(star, user, craftWine, rate);
        } else {
            //or create a new wine star
            wineEvaluation = new WineEvaluation(user, craftWine, rate);
        }

        //commit the wine grade
        wineEvaluationRepository.save(wineEvaluation);

        //count average grade for a wine
        Short averageRateForTheWine = wineEvaluationRepository.getAverageRateForTheWine(craftWine);

//        if (averageRateForTheWine == null) {
//            averageRateForTheWine = 0;
//        }
        //set average grade for a wine
        craftWine.setEvaluation(averageRateForTheWine);
        //commit average grade for a wine
        craftWineService.save(craftWine);


        return true;
    }
}
