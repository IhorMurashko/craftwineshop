package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.RegionDTO;
import com.craftWine.shop.dto.producedCountryDTO.ProducedCountryDTO;
import com.craftWine.shop.dto.producedCountryDTO.ProducedCountryResponseWithSetRegionsDTO;
import com.craftWine.shop.dto.wineDTO.CraftWineDTO;
import com.craftWine.shop.dto.wineDTO.CraftWineDTOResponse;
import com.craftWine.shop.enumTypes.SugarConsistency;
import com.craftWine.shop.enumTypes.WineColor;
import com.craftWine.shop.exceptions.NotFoundException;
import com.craftWine.shop.mapper.CraftWineMapper;
import com.craftWine.shop.mapper.ProducedCountryMapper;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.ProducedCountry;
import com.craftWine.shop.models.Region;
import com.craftWine.shop.service.CraftWineService;
import com.craftWine.shop.service.ProducedCountryService;
import com.craftWine.shop.service.WineStarService;
import com.craftWine.shop.utils.ImagineHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private CraftWineService craftWineService;
    @Mock
    private ProducedCountryService producedCountryService;
    @Mock
    private WineStarService wineStarService;
    @Mock
    private CraftWineMapper craftWineMapper;
    @Mock
    private ProducedCountryMapper producedCountryMapper;
    @Mock
    private ImagineHandler imagineHandler;
    @InjectMocks
    private AdminController adminController;


    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;
    @Captor
    private ArgumentCaptor<CraftWine> craftWineArgumentCaptor;

    private List<ProducedCountry> producedCountryList;
    private List<ProducedCountryResponseWithSetRegionsDTO> producedCountryResponseWithSetRegionsDTOList;


    private ProducedCountryResponseWithSetRegionsDTO producedCountryResponseWithSetRegionsDTO;
    private CraftWineDTO craftWineDTO;
    private ProducedCountryDTO producedCountryDTO;
    private RegionDTO regionDTO;
    private Region region;
    private CraftWine craftWine;
    private CraftWineDTOResponse craftWineDTOResponse;
    private String imageUrl;
    private Long wineId;


    @BeforeEach
    void setUp() {
        wineId = 1L;
        producedCountryResponseWithSetRegionsDTO = new ProducedCountryResponseWithSetRegionsDTO(1L, "France", new HashSet<>());

        ProducedCountry countryFrance = new ProducedCountry("France");
        countryFrance.setId(1L);
        countryFrance.setRegions(new HashSet<>());

        producedCountryList = List.of(countryFrance);
        producedCountryDTO = new ProducedCountryDTO(1L, "Spain");
        regionDTO = new RegionDTO(1L, "Region");
        region = Mockito.mock(Region.class);

        Region regionOfFrance = new Region();
        regionOfFrance.setId(1L);
        regionOfFrance.setName("region of France");
        regionOfFrance.setProducedCountry(countryFrance);

        craftWine = new CraftWine();
        craftWine.setId(wineId);
        craftWine.setWineName("wine name");
        craftWine.setPrice(new BigDecimal("120.0"));
        craftWine.setDiscount(0.0f);
        craftWine.setWineDescription("description");
        craftWine.setQuantity((short) 5);
        craftWine.setBottleCapacity("0.7");
        craftWine.setAlcohol("12");
        craftWine.setNewCollection(true);
        craftWine.setBestSeller(false);
        craftWine.setSale(false);
        craftWine.setWinemaking("winemaking");
        craftWine.setGrapeVarieties("grapeVarieties");
        craftWine.setTastingNotes("testingNotes");
        craftWine.setStoreAndServeAdvices("storeAndServeAdvices");
        craftWine.setFoodPairing("foodPairing");
        craftWine.setReviewsAndAwards("reviewsAndAwards");
        craftWine.setWineColor(WineColor.RED);
        craftWine.setSugarConsistency(SugarConsistency.DRY);
        craftWine.setRegion(regionOfFrance);
        craftWine.setImageUrl(imageUrl + wineId + ".jpg");

        imageUrl = "/home/developer/my folder/java_projects/craftWineShop/CraftWineShop/src/main/wine_images/wine-";

        craftWineDTO = new CraftWineDTO("wine name", new BigDecimal("120.0"), 0.0f,
                "description", (short) 5, "0.7", "12", true,
                false, false, "winemaking", "grapeVarieties",
                "testingNotes", "storeAndServeAdvices", "foodPairing",
                "reviewsAndAwards", "Red", "Dry", region,
                null, null);

        craftWineDTOResponse = new CraftWineDTOResponse(wineId, "wine name", new BigDecimal("120.0"),
                0.0f, new BigDecimal("0.0"), "description", (short) 5, "0.7",
                "12", true, false, false, "winemaking",
                "grapeVarieties", "testingNotes", "storeAndServeAdvices",
                "foodPairing", "reviewsAndAwards", "Red",
                "Dry", producedCountryDTO, regionDTO, (short) 0, new ArrayList<>(),
                0L, LocalDateTime.now().minusHours(2), imageUrl + wineId + ".jpg");

    }


    @Test
    @DisplayName("/admin/attributes_for_add_new_wine status OK, List size = 3")
    void getAttributesForAddNewWine() {

        doReturn(producedCountryList).when(producedCountryService).findAll();
        doReturn(producedCountryResponseWithSetRegionsDTO).when(producedCountryMapper).toProducedCountryResponseDTO(any());

        ResponseEntity<?> responseEntity = this.adminController.attributes_for_add_new_wine();


        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());

        assertNotNull(responseEntity);
        verify(producedCountryService, times(1)).findAll();
        verifyNoMoreInteractions(producedCountryService);

        Object body = responseEntity.getBody();

        assertInstanceOf(List.class, body);
        assertInstanceOf(List.class, ((List<?>) body).get(0));
        assertInstanceOf(List.class, ((List<?>) body).get(1));
        assertInstanceOf(List.class, ((List<?>) body).get(2));

        assertEquals(3, ((List<?>) body).size());


        List<String> sugarConsistencyList = (List<String>) ((List<?>) body).get(0);
        List<String> wineColorList = (List<String>) ((List<?>) body).get(1);
        List<ProducedCountryResponseWithSetRegionsDTO> producedCountryListFromResponse = (List<ProducedCountryResponseWithSetRegionsDTO>) ((List<?>) body).get(2);


        List<String> arraysSugarConsistencyList = Arrays.stream(SugarConsistency.values())
                .map(SugarConsistency::getName).toList();

        List<String> arraysWineColorList = Arrays.stream(WineColor.values())
                .map(WineColor::getName).toList();


        assertEquals(arraysSugarConsistencyList.size(), sugarConsistencyList.size());
        assertEquals(arraysSugarConsistencyList, sugarConsistencyList);

        assertEquals(arraysWineColorList.size(), wineColorList.size());
        assertEquals(arraysWineColorList, wineColorList);

        assertEquals(producedCountryList.size(), producedCountryListFromResponse.size());
        assertEquals(producedCountryResponseWithSetRegionsDTO, producedCountryListFromResponse.get(0));


        verify(producedCountryMapper, times(producedCountryList.size())).toProducedCountryResponseDTO(any());
        verifyNoMoreInteractions(producedCountryMapper);

    }


    @Test
    @DisplayName("/admin/save_a_new_wine HttpStatus CREATED")
    void saveANewWineStatusCREATED() throws IOException {

        ArgumentCaptor<CraftWineDTO> craftWineDTOArgumentCaptor = ArgumentCaptor.forClass(CraftWineDTO.class);
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        MockMultipartFile wineImage = new MockMultipartFile(
                "wineImage",
                "test-image.jpg",
                "image/jpeg",
                "Test image content".getBytes());

        Long lastCraftWineId = 0L;

        doReturn(lastCraftWineId).when(craftWineService).getLastCraftWineId();
        doReturn(craftWine).when(craftWineService).save(any(CraftWineDTO.class), anyString());
        doReturn(craftWineDTOResponse).when(craftWineMapper).toDTOResponse(craftWine);

        ResponseEntity<CraftWineDTOResponse> responseEntity = this.adminController.saveNewWine(craftWineDTO, wineImage);
        verify(craftWineService).save(craftWineDTOArgumentCaptor.capture(), stringArgumentCaptor.capture());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        assertEquals(craftWineDTOResponse, responseEntity.getBody());

        verify(craftWineService, times(1)).getLastCraftWineId();
        verify(craftWineService, times(1)).save(any(), any());
        verifyNoMoreInteractions(craftWineService);


        verify(craftWineMapper, times(1)).toDTOResponse(any());


        assertEquals(craftWineDTO, craftWineDTOArgumentCaptor.getValue());
        assertEquals(imageUrl + wineId + ".jpg", stringArgumentCaptor.getValue());

    }


    @Test
    @DisplayName("/admin/save_a_new_wine throws Exception")
    void saveANewWineThrowsAnException() throws IOException {

        MockMultipartFile emptyMultipartFile = new MockMultipartFile("emptyFile", new byte[0]);


        RuntimeException exception = assertThrows(RuntimeException.class, () -> this.adminController
                .saveNewWine(craftWineDTO, emptyMultipartFile));

        assertEquals(IllegalArgumentException.class, exception.getClass());
        assertEquals("Wine image can't be empty", exception.getMessage());

        verifyNoInteractions(craftWineService, craftWineMapper);
    }


    @Test
    @DisplayName("/admin/get/{id} HttpStatus OK")
    void getWineByIdHttpStatusOk() {
        doReturn(craftWine).when(craftWineService).findById(wineId);
        doReturn(craftWineDTOResponse).when(craftWineMapper).toDTOResponse(craftWine);

        ResponseEntity<CraftWineDTOResponse> responseEntity = this.adminController.getWineById(wineId);
        verify(craftWineService).findById(longArgumentCaptor.capture());
        verify(craftWineMapper).toDTOResponse(craftWineArgumentCaptor.capture());


        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(craftWineDTOResponse, responseEntity.getBody());
        assertEquals(wineId, longArgumentCaptor.getValue());
        assertEquals(craftWine, craftWineArgumentCaptor.getValue());


        verify(craftWineService, times(1)).findById(wineId);
        verify(craftWineMapper, times(1)).toDTOResponse(craftWine);
        verifyNoMoreInteractions(craftWineService, craftWineMapper);
    }

    @Test
    @DisplayName("/admin/get/{id} HttpStatus Not Found")
    void getWineByIdHttpStatusNotFound() {

        doThrow(NotFoundException.class).when(craftWineService).findById(wineId);

        RuntimeException exception = assertThrows(NotFoundException.class, () -> this.adminController.getWineById(wineId));
        verify(craftWineService).findById(longArgumentCaptor.capture());

        assertEquals(NotFoundException.class, exception.getClass());
        assertEquals(wineId, longArgumentCaptor.getValue());


        verify(craftWineService).findById(wineId);
        verifyNoMoreInteractions(craftWineService);

        verifyNoInteractions(craftWineMapper);
    }


    @Test
    @DisplayName("/admin/delete/{id} HttpStatus No Content")
    void deleteWineByIdHttpStatusNoContent() throws IOException {

        doReturn(true).when(craftWineService).deleteCraftWineById(wineId);

        ResponseEntity<HttpStatus> responseEntity = this.adminController.deleteWineFromShop(wineId);
        verify(craftWineService, times(1)).deleteCraftWineById(wineId);
        verify(craftWineService).deleteCraftWineById(longArgumentCaptor.capture());

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        assertEquals(wineId, longArgumentCaptor.getValue());
        verify(craftWineService, times(1)).deleteCraftWineById(wineId);
    }


    @Test
    @DisplayName("/admin/delete/{id} HttpStatus BadRequest")
    void deleteWineByIdHttpStatusBadRequest() throws IOException {

        doThrow(NotFoundException.class).when(craftWineService).deleteCraftWineById(wineId);

        RuntimeException exception = assertThrows(NotFoundException.class, () -> this.adminController.deleteWineFromShop(wineId));
        verify(craftWineService).deleteCraftWineById(longArgumentCaptor.capture());


        assertEquals(wineId, longArgumentCaptor.getValue());
        assertEquals(NotFoundException.class, exception.getClass());
        verify(craftWineService, times(1)).deleteCraftWineById(wineId);
        verifyNoMoreInteractions(craftWineService);
    }


    @Test
    @DisplayName("/admin/update/{id} HttpStatus OK with empty picture")
    void updateWineByIdHttpStatusOkWithEmptyPicture() throws IOException {

        MockMultipartFile emptyMultipartFile = new MockMultipartFile("emptyFile", new byte[0]);

        doReturn(craftWine).when(craftWineService).findById(wineId);
        doReturn(craftWine).when(craftWineMapper).toEntityCraftWine(craftWineDTO);
        doReturn(true).when(craftWineService).save(craftWine);
        doReturn(craftWineDTOResponse).when(craftWineMapper).toDTOResponse(craftWine);

        ResponseEntity<CraftWineDTOResponse> responseEntity = this.adminController.updateWine(wineId, craftWineDTO, null);
        verify(craftWineService).findById(longArgumentCaptor.capture());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(craftWineDTOResponse, responseEntity.getBody());

        assertEquals(wineId, longArgumentCaptor.getValue());

        verify(craftWineMapper, times(1)).toEntityCraftWine(craftWineDTO);

        verify(craftWineService, times(1)).findById(wineId);

        verify(craftWineService, times(1)).save(any());
        verifyNoMoreInteractions(craftWineService, craftWineMapper);
    }

    @Test
    @DisplayName("/admin/update/{id} HttpStatus OK with picture is present")
    void updateWineByIdHttpStatusOkWithPictureIsPresent() throws IOException {

        MockMultipartFile emptyMultipartFile = new MockMultipartFile("emptyFile", new byte[0]);

        doReturn(craftWine).when(craftWineService).findById(wineId);
        doReturn(craftWine).when(craftWineMapper).toEntityCraftWine(craftWineDTO);
        doReturn(true).when(craftWineService).save(craftWine);
        doReturn(craftWineDTOResponse).when(craftWineMapper).toDTOResponse(craftWine);

        ResponseEntity<CraftWineDTOResponse> responseEntity = this.adminController.updateWine(wineId, craftWineDTO, emptyMultipartFile);
        verify(craftWineService).findById(longArgumentCaptor.capture());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(craftWineDTOResponse, responseEntity.getBody());

        assertEquals(wineId, longArgumentCaptor.getValue());

        verify(craftWineMapper, times(1)).toEntityCraftWine(craftWineDTO);

        verify(craftWineService, times(1)).findById(wineId);

        verify(craftWineService, times(1)).save(any());
        verifyNoMoreInteractions(craftWineService, craftWineMapper);
    }

    @Test
    @DisplayName("/admin/update/{id} HttpStatus BadRequest wine with id NotFoundException")
    void updateWineByIdHttpStatusBadRequestNotFoundException() throws IOException {

        doThrow(NotFoundException.class).when(craftWineService).findById(wineId);

        RuntimeException exception = assertThrows(NotFoundException.class,
                () -> this.adminController.updateWine(wineId, craftWineDTO, null));

        assertEquals(NotFoundException.class, exception.getClass());
        verify(craftWineService).findById(longArgumentCaptor.capture());

        assertEquals(wineId, longArgumentCaptor.getValue());

        verifyNoMoreInteractions(craftWineService);
        verifyNoInteractions(craftWineMapper);
    }
}