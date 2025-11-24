package com.example.umc9th.global.init;

import com.example.umc9th.domain.location.entity.Location;
import com.example.umc9th.domain.location.repository.LocationRepository;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.repository.StoreRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitData {

    private final InitService initService;

    public InitData(InitService initService) {
        this.initService = initService;
    }

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    @Transactional
    static class InitService {

        private final LocationRepository locationRepository;
        private final StoreRepository storeRepository;

        public InitService(LocationRepository locationRepository,
                           StoreRepository storeRepository) {
            this.locationRepository = locationRepository;
            this.storeRepository = storeRepository;
        }

        public void init() {

            // Location 하나만 생성
            Location loc = Location.builder()
                    .name("서울 강남구")
                    .build();
            locationRepository.save(loc);

            // Store 생성
            Store store = Store.builder()
                    .name("UMC 카페")
                    .detailAddress("강남대로 123")
                    .location(loc)
                    .build();
            storeRepository.save(store);
        }
    }
}
