## Mobilszoftver Laboratórium
### 2025 tavaszi félév
### Sági Benedek - (ECSGGY)
### Laborvezető: Sík Dávid

## Festmény alkalmazás

<img src="./assets/icon.webp" width="160">


## Bemutatás (1.labor)

'Art Institute of Chicago' intézmény rajongóinak megvannak a saját festményei, de szeretnének még látni/felfedezni/keresni újabbakat, és azokat elmenteni emlékezettül, hogy megnézhesse személyesen az intézményben.
Az alkalmazásban a festmények kizárólag az Chicagói művészeti intézményből vannak.
## Főbb funkciók
### 1. Festmények keresése
- A. Cím 
- B. Alkotó neve
- C. Egyéb (pl. a cicákat tartalmazó festményeket keresel, akkor cica kulcsszó segítségével kikereshető)
Ugyanakkor nem kötelező mindegyiket kitölteni: Ha mindegyik üresen hagyja, akkor az adott (lokálisan) elmentett kulcsszavak közül véletlenszerűen választott szavakkal való keresés történik.
-  A keresés után a festményeket felsorolják (részlet nélkül)

### 2. Adott festmény részletezése
#### Festmény: Cím, szerző, tartalom, kedvencként elmentése lokálisan
Tehát az egyszerűség kedvéért kizárólag a festményeknél el lehet menteni kedvencként, meg eltávolítani a kedvencek közül.
(A szerzők felsorolását kihagyom az egyszerűség kedvéért, de hogyha mégis kevés ez, akkor utólag ezt adom hozzá.)

Usecase, és usestory ("Festmény nézegetés")

<img src="./assets/BMELaborMobilUseCaseUseStory.png" width="320">

## Képernyőtervek
1.
<img src="./assets/Main.png" width="320">
2.
<img src="./assets/Paint_Details.png" width="320">
3.
<img src="./assets/Paint_searching.png" width="320">

## Architektúra: MVVM (2.labor)
A Model-View-ViewModel egy általános architektúra, amiben van a nézet, üzleti logika, és az adat réteg, és azok "rétegesen", szeperáltan el vannak különítve.
A MVVM a Feaute-kben látható legjobban. 
Viszonylag az egész struktúra leginkább a Clean Archiktetúrán alapul, mivel vannak a DI, és a datasource mappa.
### Nézet:
- Navigation
- ui, és almappái

### Üzleti logika:
- feature, és almappái

### Adat:
- Data: Dao, DataSource, di (Hilt megoldás), entities
- Network

Azért választottam ezt, mert jelenleg ez a legjobb megoldás erre az arcitektúrálisan átfogó alkalmazás fejlesztésére,
valamint a Jetpack Compose-fejlesztésénl a ViewModel megoldás segítségével könnyebben teszi a készítését.

## Commit:
<img src="./assets/commits.png" width="320">

## Github Actions:
<img src="./assets/githubaction.png" width="320">

## Service (API), és az ORM elkészítése (Room könyvtárral) (3.labor)

### Service osztályai

#### Modellek (Felelősség: Adott entitás leírása, plusz itt is meg van határozva, hogy melyik tulajdonságokat tartsuk meg a JSON-ból):
- ArtistApi: A művész adott attribútumait leíró osztály
- ArtworkApi: A mű adott attribútumait leíró osztály
- Pagination: Az API-tól lekérdezett JSON egyik részét, Pagination, mely leírja, hogy mennyi entitást, melyik oldalon, hány oldal van összesen, és mi a következő entitástnak az URL-je, amivel lehet folytatni.
- ApiResponse: Mivel az API a következőből így épül fel: pagination: {}, data: [{},{},{}], ezért még egy osztályt kellett látrehozni.

#### API (Felelősség: API-tól való lehívás adott path-hal, query-jel):
- ArtistService: A művészekhez kapcsolódó adatok lekérése az API-tól.
- ArtworkService: A műalkotásokhoz kapcsolódó adatok lekérése az API-tól.
- ImageService: Egy adott műhöz kapcsolódó kép  lekérése az API-tól.

### ORM osztályai

#### Entity (Felelősség: A lokális adatbázishoz kapcsolódó entitások definiálása)
- Artist: Az attribútumai azonosak az ArtistApi-ével, csak ORM-nak megfelelően
- Artwork: Az attribútumai azonosak az ArtworkApi-ével, csak ORM-nak megfelelően
- ImageEntity: Eredetileg az Artwork-ba akartam beégetni, csakhogy nehezebb karbantartható lenne a kód hosszútávon (hipotizésen)
#### DAO (Felelősség: Az adatbázisműveletek (insert, delete, query) definiálása)
- ArtistDao: A művészekhez kapcsolódó adatbázisműveleteket végzi el, például beszúrás, lekérdezés.
- ArtworkDao: A műalkotásokhoz kapcsolódó adatbázisműveleteket végzi el, például új mű mentése, kedvenc művek lekérdezése.
- ImageDao: A képekhez kapcsolódó adatbázisműveleteket kezeli, például egy műhöz tartozó kép mentése, lekérdezése.

### Repositories (Felelősség: Beköti a DAO, Service adott műveleteit)
- ArtistRepository: Interfész, mely definiálja az művészekkel kapcsolatos elérhető műveleteket.
- ArtistRepositryImpl: Megvalósítja az ArtistRepository interfészt. Összeköti az ArtistService-t és az ArtistDao-t, hogy mind hálózati, mind lokális adatforrásból tudjon adatot kezelni.
- ArtworkRepositry:  Interfész, amely definiálja a műalkotásokkal kapcsolatos elérhető műveleteket
- ArtworkRepositoryImpl:  Megvalósítja az ArtworkRepository interfészt. A hálózatról (ArtworkService) szerzett adatokat kezeli, illetve a lokális adatbázisba (ArtworkDao) történő mentést intézi.
- ImageRepository:  Interfész, amely a képek letöltésével és tárolásával kapcsolatos műveleteket írja le.
- ImageRepositoryImpl: Megvalósítja az ImageRepository interfészt, kezeli az ImageService API hívásokat és az ImageDao adatbázisműveleteit.

### DI
- NetworkModule: Felelős a hálózati kapcsolatokhoz szükséges függőségek biztosításáért, mint például Retrofit példány, ArtistService, ArtworkService, ImageService inicializálása.
- PersistenceModule: Felelős a lokális adatbázishoz kapcsolódó függőségek biztosításáért, például Room adatbázis példány létrehozása, ArtistDao, ArtworkDao, ImageDao biztosítása a Hilt számára.

### Firebase

#### Események
<img src="./assets/events.png" width="320">

#### Crashlytics
<img src="./assets/crashlytics.png" width="320">

### Test units (strategy)
- Lokális: Az Android-os kontextus nélkül leteszteltem az Artwork, Artist, és ImageEntity repositoriek Dao-s, és Service-s viselkedéseit, vagyis nem volt benne sem adatbázis, sem API-lehívás.
- Instrumentális: 

#### Lokális:

1. Lokális Unit Tesztek (Local Unit Tests)
- Cél:Az ArtworkRepository, ArtistRepository és ImageRepository komponensek működésének tesztelése Android környezet nélkül.

- Technológia: JUnit4, MockK, kotlinx.coroutines.test

- Mockolt elemek: API (Service) és adatbázis (DAO) rétegek.

- Fókusz:

  - API válaszok kezelése
  - Adatbázis műveletek (insert, delete, query)
  - Kulcsszavas keresés működése

- Előny: Gyors, nem szükséges hozzá eszköz vagy emulator, jól izolálható hibák.

#### Instrumentális

- Cél: A valós Android környezetben működő komponensek (pl. Retrofit alapú Service osztályok és Room adatbázis) viselkedésének ellenőrzése.

- Technológia: AndroidJUnit4, Room, Retrofit, Gson

- Fókusz:

    - API hívások valós válaszai (ArtworkService, ArtistService)

    - Room adatbázis integrációs tesztjei

- Előny: Biztosítja, hogy az alkalmazás komponensei megfelelően integrálódnak az Android rendszerbe.


#### Tesztlefedettség:

##### Lokális tesztek (12 db):

- Artwork repository: 7 teszt (lekérdezések, mentés, törlés, keresés)

- Artist repository: 5 teszt (lekérdezések, mentés, törlés)

##### Instrumentális tesztek (8 db):
- Service tesztek (4 db):

  - Műalkotások és művészek lekérése
  - Kulcsszavas keresések API-n keresztül

- Database tesztek (4 db):

  - Műalkotás és művész beszúrás + törlés
  - Flow alapú lista lekérdezések

#### Sikerees tesztek futtatásai

<img src="./assets/ServiceInstrumentedTest_SuccesfulRunning.png" width="320">
<img src="./assets/DatabaseInstrumentedTest_SuccesfulRunning.png" width="320">
<img src="./assets/RepositoriesTest_SuccesfulRunning.png" width="320">
