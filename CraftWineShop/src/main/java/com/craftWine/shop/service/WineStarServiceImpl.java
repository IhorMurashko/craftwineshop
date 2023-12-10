package com.craftWine.shop.service;

import com.craftWine.shop.exceptions.EmailProblemException;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.User;
import com.craftWine.shop.models.WineStar;
import com.craftWine.shop.repositories.WineStarsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WineStarServiceImpl implements WineStarService {

    private final UserService userService;
    private final CraftWineService craftWineService;
    private final WineStarsRepository wineStarsRepository;


    /**
     * Adds a new user grade for a craft wine and calculates the average grade for the wine.
     * <p>
     * This method takes a unique identifier {@code userEmail} for a user, a unique identifier {@code craftWineId} for a craft wine,
     * and the {@code rate} given by the user for the craft wine.
     * </p>
     * <p>
     * The method saves the rating into the database for the user. If this is the user's first rating for the wine, it is saved as a new entry,
     * otherwise, it updates the existing rating from the user for the wine.
     * </p>
     * <p>
     * The method also calculates a new average grade for the wine and updates it in the database for the wine.
     * </p>
     *
     * @param userEmail   The unique email of the user who wants to assign a grade to the wine.
     * @param craftWineId The unique identifier of the wine in the database.
     * @param rate        The grade given by the user for the wine.
     * @return {@code true} if the update was successful.
     * @throws EmailProblemException if user with email {@code userEmail} not found
     */


    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public boolean addRateForTheWine(String userEmail, long craftWineId, short rate) {

        // get user by user's email
        Optional<User> optionalUser = userService.findUserByEmail(userEmail);

        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new EmailProblemException("Could not find user with email " + userEmail);
        }
        //get craft wine by craft wine's id;
        CraftWine craftWine = craftWineService.findById(craftWineId);


        //looking for a grade by the user for the wine
        Long existStarForTheWineByUser = wineStarsRepository.isExistStarForTheWineByUser(user, craftWine);

        WineStar wineStar = null;

        //if user has already added grade for this wine
        if (existStarForTheWineByUser != null && existStarForTheWineByUser > 0) {
            //update a grade by user for the wine
            wineStar = new WineStar(existStarForTheWineByUser, user, craftWine, rate);
        } else {
            //or create a new wine star
            wineStar = new WineStar(user, craftWine, rate);
        }

        //commit the wine grade
        wineStarsRepository.save(wineStar);

        //count average grade for a wine
        Short averageRateForTheWine = wineStarsRepository.getAverageRateForTheWine(craftWine);

//        if (averageRateForTheWine == null) {
//            averageRateForTheWine = 0;
//        }
        //set average grade for a wine
        craftWine.setRate(averageRateForTheWine);
        //commit average grade for a wine
        craftWineService.save(craftWine);


        return true;
    }
}
