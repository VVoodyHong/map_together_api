package com.mapto.api.app.follow.service;

import com.mapto.api.app.follow.dto.FollowDTO;
import com.mapto.api.app.follow.entity.Follow;
import com.mapto.api.app.follow.repository.FollowRepository;
import com.mapto.api.app.user.dto.UserDTO;
import com.mapto.api.app.user.entity.User;
import com.mapto.api.app.user.repository.UserRepository;
import com.mapto.api.common.model.CustomException;
import com.mapto.api.common.model.StatusCode;
import com.mapto.api.common.model.type.FollowType;
import com.mapto.api.common.util.CheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createFollow(Long fromUserIdx, Long toUserIdx) throws CustomException {
        if(CheckUtil.isNullObject(toUserIdx)) {
            throw new CustomException(StatusCode.CODE_851);
        } else {
            FollowDTO.State state = getFollowState(fromUserIdx, toUserIdx);
            if(!state.isFollow()) {
                User fromUser = userRepository.findByIdx(fromUserIdx);
                User toUser = userRepository.findByIdx(toUserIdx);
                Follow follow = Follow.builder()
                        .fromUser(fromUser)
                        .toUser(toUser)
                        .build();
                followRepository.save(follow);
            }
        }
    }

    @Transactional(readOnly = true)
    public FollowDTO.Simples searchFollow(FollowDTO.Search search) throws CustomException {
        if(CheckUtil.isNullObject(search.getUserIdx())) {
            throw new CustomException(StatusCode.CODE_851);
        } else if(CheckUtil.isNullObject(search.getFollowType())) {
            throw new CustomException(StatusCode.CODE_852);
        } else if(CheckUtil.isNullObject(search.getKeyword())) {
            throw new CustomException(StatusCode.CODE_853);
        } else {
            List<Follow> followList = followRepository.findByUserIdx(search.getUserIdx(), search.getFollowType());
            List<Long> userIdxList = new ArrayList<>();
            for(Follow follow : followList) {
                if(search.getFollowType().equals(FollowType.FOLLOWING)) {
                    userIdxList.add(follow.getToUser().getIdx());
                } else {
                    userIdxList.add(follow.getFromUser().getIdx());
                }
            }
            Pageable pageable = search.getRequestPage().of();
            Page<User> userPage = userRepository.findByIdxInAndKeyword(userIdxList, search.getKeyword(), pageable);
            FollowDTO.Simples result = new FollowDTO.Simples();
            List<UserDTO.Simple> list = new ArrayList<>();
            for(User user : userPage) {
                UserDTO.Simple simple = new UserDTO.Simple();
                simple.setIdx(user.getIdx());
                simple.setName(user.getName());
                simple.setNickname(user.getNickname());
                simple.setProfileImg(user.getProfileImg());
                list.add(simple);
            }
            result.setList(list);
            result.setTotalCount(userPage.getTotalElements());
            result.setLast(userPage.isLast());
            return result;
        }
    }

    @Transactional(readOnly = true)
    public FollowDTO.Count getFollowCount(Long userIdx) throws CustomException {
        if(CheckUtil.isNullObject(userIdx)) {
            throw new CustomException(StatusCode.CODE_851);
        } else {
            int following = followRepository.findByUserIdx(userIdx, FollowType.FOLLOWING).size();
            int follower = followRepository.findByUserIdx(userIdx, FollowType.FOLLOWER).size();
            FollowDTO.Count count = new FollowDTO.Count();
            count.setFollowing(following);
            count.setFollower(follower);
            return count;
        }
    }

    @Transactional(readOnly = true)
    public FollowDTO.State getFollowState(Long fromUserIdx, Long toUserIdx) throws CustomException {
        if(CheckUtil.isNullObject(toUserIdx)) {
            throw new CustomException(StatusCode.CODE_851);
        } else {
            Optional<Follow> follow = followRepository.findByFromUserIdxAndToUserIdx(fromUserIdx, toUserIdx);
            FollowDTO.State state = new FollowDTO.State();
            state.setFollow(follow.isPresent());
            return state;
        }
    }


    @Transactional
    public void deleteFollow(Long fromUserIdx, Long toUserIdx) throws CustomException {
        if(CheckUtil.isNullObject(toUserIdx)) {
            throw new CustomException(StatusCode.CODE_851);
        } else {
            FollowDTO.State state = getFollowState(fromUserIdx, toUserIdx);
            if(state.isFollow()) {
                followRepository.deleteByFromUserIdxAndToUserIdx(fromUserIdx, toUserIdx);
            }
        }
    }
}
