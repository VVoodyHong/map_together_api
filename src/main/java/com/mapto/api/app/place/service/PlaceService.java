package com.mapto.api.app.place.service;

import com.mapto.api.app.file.dto.FileDTO;
import com.mapto.api.app.file.entity.File;
import com.mapto.api.app.file.repository.FileRepository;
import com.mapto.api.app.place.dto.PlaceDTO;
import com.mapto.api.app.place.dto.PlaceLikeDTO;
import com.mapto.api.app.place.entity.Place;
import com.mapto.api.app.place.entity.PlaceFile;
import com.mapto.api.app.place.entity.PlaceLike;
import com.mapto.api.app.place.entity.PlaceTag;
import com.mapto.api.app.place.repository.PlaceFileRepository;
import com.mapto.api.app.place.repository.PlaceLikeRepository;
import com.mapto.api.app.place.repository.PlaceRepository;
import com.mapto.api.app.place.repository.PlaceTagRepository;
import com.mapto.api.app.placecategory.entity.PlaceCategory;
import com.mapto.api.app.placecategory.repository.PlaceCategoryRepository;
import com.mapto.api.app.tag.dto.TagDTO;
import com.mapto.api.app.tag.entity.Tag;
import com.mapto.api.app.tag.repository.TagRepository;
import com.mapto.api.app.user.entity.User;
import com.mapto.api.app.user.repository.UserRepository;
import com.mapto.api.common.file.FileUploader;
import com.mapto.api.common.model.CustomException;
import com.mapto.api.common.model.StatusCode;
import com.mapto.api.common.util.CheckUtil;
import com.mapto.api.config.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final PlaceCategoryRepository placeCategoryRepository;
    private final PlaceFileRepository placeFileRepository;
    private final FileRepository fileRepository;
    private final PlaceTagRepository placeTagRepository;
    private final TagRepository tagRepository;
    private final FileUploader fileUploader;
    private final PlaceLikeRepository placeLikeRepository;

    @Transactional
    public PlaceDTO.Basic createPlace(UserPrincipal userPrincipal, PlaceDTO.Create placeInfo, List<MultipartFile> files) throws CustomException, IOException {
        if(CheckUtil.isEmptyString(placeInfo.getName())) {
            throw new CustomException(StatusCode.CODE_753);
        } else if(CheckUtil.isEmptyString(placeInfo.getAddress())) {
            throw new CustomException(StatusCode.CODE_754);
        } else if(placeInfo.getCategoryIdx() == -1) {
            throw new CustomException(StatusCode.CODE_755);
        }

        User user = userRepository.findByIdx(userPrincipal.getUser().getIdx());
        PlaceCategory placeCategory = placeCategoryRepository.findByIdx(placeInfo.getCategoryIdx());
        Place place = Place.builder()
                .user(user)
                .category(placeCategory)
                .name(placeInfo.getName())
                .address(placeInfo.getAddress())
                .description(placeInfo.getDescription())
                .favorite(placeInfo.getFavorite())
                .lat(placeInfo.getLat())
                .lng(placeInfo.getLng())
                .build();
        Place placeEntity = placeRepository.save(place);

        List<Tag> tagEntities = new ArrayList<>();
        List<PlaceTag> placeTagEntities = new ArrayList<>();
        for(String tagName : placeInfo.getTags()) {
            Optional<Tag> findTag = tagRepository.findTagByName(tagName);
            Tag tag = findTag.orElseGet(() -> Tag.builder()
                    .name(tagName)
                    .build());
            PlaceTag placeTag = PlaceTag.builder()
                    .place(placeEntity)
                    .tag(tag)
                    .build();
            tagEntities.add(tag);
            placeTagEntities.add(placeTag);
        }
        tagRepository.saveAll(tagEntities);
        placeTagRepository.saveAll(placeTagEntities);

        if(files != null && files.size() != 0) {
            List<FileDTO.Basic> fileList = fileUploader.upload(files, "place");
            List<File> fileEntities = new ArrayList<>();
            List<PlaceFile> placeFileEntities = new ArrayList<>();
            boolean isFirst = true;
            for (FileDTO.Basic basic : fileList) {
                if(isFirst) placeEntity.setRepresentImg(basic.getUrl());
                File file = File.builder()
                        .name(basic.getName())
                        .type(basic.getType())
                        .url(basic.getUrl())
                        .build();
                PlaceFile placeFile = PlaceFile.builder()
                        .place(placeEntity)
                        .file(file)
                        .build();
                fileEntities.add(file);
                placeFileEntities.add(placeFile);
                isFirst = false;
            }
            placeRepository.save(placeEntity);
            fileRepository.saveAll(fileEntities);
            placeFileRepository.saveAll(placeFileEntities);
        }
        return placeEntity.toPlaceBasicDTO();
    }

    @Transactional
    public FileDTO.Simples getPlaceImage(Long placeIdx) throws CustomException {
        if(CheckUtil.isNullObject(placeIdx)) {
            throw new CustomException(StatusCode.CODE_757);
        }
        List<File> files = fileRepository.findByPlaceIdx(placeIdx);
        FileDTO.Simples result = new FileDTO.Simples();
        List<FileDTO.Simple> list = new ArrayList<>();
        for (File file : files) {
            list.add(file.toFileSimpleDTO());
        }
        result.setList(list);
        return result;
    }

    @Transactional
    public TagDTO.Simples getPlaceTag(Long placeIdx) throws CustomException {
        if(CheckUtil.isNullObject(placeIdx)) {
            throw new CustomException(StatusCode.CODE_757);
        }
        List<Tag> tags = tagRepository.findByPlaceIdx(placeIdx);
        TagDTO.Simples result = new TagDTO.Simples();
        List<TagDTO.Simple> list = new ArrayList<>();
        for (Tag tag : tags) {
            list.add(tag.toTagSimpleDTO());
        }
        result.setList(list);
        return result;
    }

    @Transactional
    public void createPlaceLike(UserPrincipal userPrincipal, Long placeIdx) throws CustomException {
        if(CheckUtil.isNullObject(placeIdx)) {
            throw new CustomException(StatusCode.CODE_757);
        }
        User user = userRepository.findByIdx(userPrincipal.getUser().getIdx());
        Place place = placeRepository.findByIdx(placeIdx);
        PlaceLike placeLike = PlaceLike
                .builder()
                .user(user)
                .place(place)
                .build();
        placeLikeRepository.save(placeLike);
    }

    @Transactional(readOnly = true)
    public PlaceLikeDTO.Simple getPlaceLike(UserPrincipal userPrincipal, Long placeIdx) throws CustomException {
        if(CheckUtil.isNullObject(placeIdx)) {
            throw new CustomException(StatusCode.CODE_757);
        }
        PlaceLike placeLike = placeLikeRepository.findByUserIdxAndPlaceIdx(userPrincipal.getUser().getIdx(), placeIdx);
        Integer totalLike = placeLikeRepository.findByPlaceIdx(placeIdx).size();
        PlaceLikeDTO.Simple placeLikeSimple = new PlaceLikeDTO.Simple();
        placeLikeSimple.setTotalLike(totalLike);
        placeLikeSimple.setLike(placeLike != null);
        return placeLikeSimple;
    }

    @Transactional
    public void deletePlaceLike(UserPrincipal userPrincipal, Long placeIdx) throws CustomException {
        if(CheckUtil.isNullObject(placeIdx)) {
            throw new CustomException(StatusCode.CODE_757);
        }
        placeLikeRepository.deleteByUserIdxAndPlaceIdx(userPrincipal.getUser().getIdx(), placeIdx);
    }

    @Transactional(readOnly = true)
    public PlaceDTO.Simples searchPlace(Long userIdx, PlaceDTO.Search search) throws CustomException {
        if(CheckUtil.isEmptyString(search.getKeyword())) {
            throw new CustomException(StatusCode.CODE_761);
        } else if(CheckUtil.isNullObject(search.getAddress())) {
            throw new CustomException(StatusCode.CODE_762);
        } else {
            Pageable pageable = search.getRequestPage().of();
            Page<PlaceDTO.Simple> placePage = placeRepository.findByKeywordAndAddress(userIdx, pageable, search.getKeyword(), search.getAddress());
            PlaceDTO.Simples result = new PlaceDTO.Simples();
            result.setList(placePage.getContent());
            result.setTotalCount(placePage.getTotalElements());
            result.setLast(placePage.isLast());
            return result;
        }
    }
}

