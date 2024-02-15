package com.craftWine.shop.service.wineCommentServices;

import com.craftWine.shop.exceptions.EmailProblemException;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.User;
import com.craftWine.shop.models.WineComment;
import com.craftWine.shop.repositories.WineCommentRepository;
import com.craftWine.shop.security.TokenProvider;
import com.craftWine.shop.service.crafWineServices.CraftWineService;
import com.craftWine.shop.service.userServices.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WineCommentServiceImpl implements WineCommentService {


    private final UserService userService;
    private final CraftWineService craftWineService;
    private final WineCommentRepository wineCommentRepository;
    private final TokenProvider tokenProvider;


    @Override
    public boolean addComment(long wineId, String token, String userComment) {


        String userEmailFromToken = tokenProvider.extractUsername(token);

        User user = userService.findUserByEmail(userEmailFromToken).orElseThrow(() ->
                new EmailProblemException("Couldn't  find user with email " + userEmailFromToken));

        CraftWine craftWine = craftWineService.findById(wineId);


        WineComment wineComment = new WineComment();

        wineComment.setUser(user);
        wineComment.setCraftWine(craftWine);
        wineComment.setComment(userComment);
        wineComment.setAddedCommentTime(LocalDateTime.now());

        wineCommentRepository.saveAndFlush(wineComment);

        return true;
    }
}
