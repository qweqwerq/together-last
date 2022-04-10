package com.project.together.service;

import com.project.together.entity.*;
import com.project.together.repository.ItemRepository;
import com.project.together.repository.UserRepository;
import com.project.together.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishService {

    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Wish Wish(Long userIdx, Long itemId) {
        //엔티티 조회
        User user = userRepository.findByIdx(userIdx);
        Item item = itemRepository.findOne(itemId);

        //위시리스트에 추가할 아이템 생성
        WishItem wishItem = WishItem.createWishItem(item);

        //찜 생성
        Wish wish = Wish.createWish(user, wishItem);

        wishRepository.save(wish);

        return wish;
    }

    public void checkDuplicate(Long userIdx) {

        List<Wish> wishList = wishRepository.findByUser(userIdx);

        for(int i = 0; i < wishList.size(); i++) {
            for(int j = 0; j <wishList.size(); j++) {
                if(wishList.get(i).getWishItem().getItem().equals(wishList.get(j).getWishItem().getItem())){
                    throw new IllegalStateException("이미 찜한 상품입니다.");
                }
            }
        }
    }

    public List<Wish> findByUser(Long userIdx) {
        return wishRepository.findByUser(userIdx);
    }

    public void removeWish(Long userIdx, Long itemId) {
        wishRepository.findByItem(userIdx, itemId).get(0).setIsCancel(1);
    }


}