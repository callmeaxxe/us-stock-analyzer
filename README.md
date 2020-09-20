# US stock analyzer

<img width="939" src="https://user-images.githubusercontent.com/35681772/93719869-5ebda180-fbc0-11ea-82a4-fe62713cfbc8.png">

US stock symbol(e.g. AAPL, GOOG) 입력시 data(for 180 days) 를 바탕으로 __max profit__ 을 return 하는 analyzer

처음으로 Kotlin 을 사용하여 프로젝트를 진행하여 다소 어색하거나 부족한 표현이 있을 수 있습니다.

---

## Requirements
 * Algorithm
   - Correctly and efficiently return the max profit

 * Design
   - Provide clear abstractions between the api, business, and data layer
   - The third-party data source should be easily interchangeable
   - Components are reusable/testable

 * Testing
   - The profit calculation is thoroughly tested __(Consider the different ways price history can vary)__
   - Other components are reasonably tested
   - Submissions without tests will be rejected

---

## Tech Stack
 * Kotlin for Web Back-End
 * Javascript, HTML/CSS for Web Front-End
 * Spring for Web Framework
 * Gradle for build tool

---

## Architecture

아키텍처는 MVC 패턴을 고려하여 패키징 하고 관심을 분리해 냈습니다.

<img width="998" src="https://user-images.githubusercontent.com/35681772/93718410-f4543380-fbb6-11ea-9d70-dc2f2371890b.png">

> 인터페이스를 이용해서 구현한 StockFetcher 계층

`StockFetcher` 의 경우 다양한 구현체가 있을 수 있을 것이라고 생각해서 인터페이스로 분리해 냈습니다.
이 프로젝트에서는 [Yahoo Finance](https://rapidapi.com/apidojo/api/yahoo-finance1) 를 사용하여 historical data 를 가져왔으나, 
US Stock data 관련 API 는 매우 다양하기 때문에 구현체가 변경될 여지가 크다고 판단했습니다.

따라서 변경의 여파를 최소화 하기 위해 인터페이스로 분리하여 설계하였습니다.

마찬가지로 Yahoo Finance API 를 사용하더라도, 실제 Deprecating 된 API 들이 존재하는 use case 를 확인할 수 있듯, 여
API 내부에서도 변경이 있을 수 있을 것이라 판단하여 각 API 별 DTO 를 따로 두는 식으로 처리하였습니다.

한 계층에서의 변경이 다른 계층까지 변경의 영향을 전파하지 않도록 하기 위해 의존성을 연결하는 과정에 있어 
변경의 여지가 큰 구현체가 아니라 변경의 여지가 적은 인터페이스에 의존성을 연결하도록 설계하였습니다.

---

## Algorithm

180 일 간의 데이터를 바탕으로 Max Profit 을 산출하는 buy date 와 sell date 를 도출해내는 알고리즘은 다음과 같습니다.

처음 접근은 naive 하게 전체 데이터를 탐색하며 최저점과 최고점을 뽑아냈습니다. 
여기서 Edge case 가 발생하였는데, Highest value 가 Lowest Value 보다 선행되는 경우가 그 것입니다.

최저점에 매매를 하고, 최고점에 매도를 해야 Max Profit 을 산출할 수 있을 것이라 이해를 하였기 때문에, 위 case 의 경우 최고점이 선행하고 있으므로 
의도한 Max Profit 을 도출해낼 수 없었습니다.

따라서 이 경우(Highest Value 가 Lowest Value 보다 선행하는 경우) 다시 탐색을 시도합니다. 
처음부터 Highest Value 까지 탐색하며 (처음 ~ Highest Value) 범위 내에서 최저점을 찾아냅니다.
마찬가지로, (Lowest Value ~ 끝) 범위 내에서 최고점을 찾아냅니다.
이렇게 각각 두 경우의 Profit 을 찾아 비교해서 Max Profit 을 찾도록 알고리즘을 설계했습니다.

고안한 알고리즘은 O(N) 에 수행이 가능합니다.      

---

## Issue
프로젝트를 진행하며 발생한 이슈로는 다음과 같은 것들이 있습니다.

#### 180 일 간의 데이터를 가져오는 과정에서 Zone Id 문제
US Stock market historical data 를 가져올 API 로 [Yahoo Finance](https://rapidapi.com/apidojo/api/yahoo-finance1) 를 사용했습니다.
Yahoo Finance API 에서 Stock historical data 를 가져올 때 
From, To 와 같은 parameter 를 기입하게 하여 API 단에서 특정 range 만큼의 데이터만 가져올 수 있기를 기대했으나,
해당 API 들은 Deprecating 되어 사용할 수 없었고, 사용 가능한 API(stock/v3/get-historical-data) 에서는 날짜를 특정할 수 없었습니다.

따라서 해당 API 를 호출하여 받을 수 있는 전체 데이터를 받은 다음 서버단 코드에서 180일에 대한 데이터를 뽑아내는 작업을 수행했습니다.

여기서 처음 시도한 부분으로 180일을 정확하게 계산하기 위해 `Unix Time` 으로 기입된 Date 값을 
`LocalDateTime` 타입으로 캐스팅하여 `minusDays(180)` 과 같은 함수를 호출하여 보다 정확하게(하드코딩을 하지 않는 방향으로) 해결하려고 시도했습니다.

이 과정에서 `ZoneId` 값을 입력했어야 했는데, Yahoo Finance 에서 확인할 수 있는 ZoneId 정보가 없었고(단순히 region = US 정도) 
따라서 정확한 계산이 제한되었습니다.

<img width="596" alt="Screen Shot 2020-09-21 at 2 16 54 AM" src="https://user-images.githubusercontent.com/35681772/93717405-945a8e80-fbb0-11ea-99db-eebaa5d2929c.png">

> 확인할 수 있던 region 정보. US 지역 내에서도 시간은 여러개로 나뉘는 것으로 알고 있음. 

결과적으로 180개의 데이터만 카운팅 하는식으로 해결하였으나, 보다 정확한 연산을 거치지 못한 점이 아쉽습니다.   

#### Yahoo Finance API 의 응답 불안정 문제 (503 ERROR)

<img width="1332" src="https://user-images.githubusercontent.com/35681772/93718978-9c1f3080-fbba-11ea-8683-a18ce895efbb.png">

처음엔 제한된 쿼리 수를 모두 사용한 것이거나 발급 받은 인증 키 의 유효성이 만료된 것이라 생각했지만, 추후에 시간이 좀 지나서 다시 테스트를 돌려보니 잘 동작하였습니다.
따라서 해당 API 를 제공하는 서버가 불안정 한 것이라 판단하였습니다. 

---

## Achievement

<img width="774" src="https://user-images.githubusercontent.com/35681772/93721770-18bb0a80-fbcd-11ea-9cbd-1261d1751958.png">
<img width="779" src="https://user-images.githubusercontent.com/35681772/93721766-1658b080-fbcd-11ea-9c21-058ebb963ca7.png">

작성한 코드는 테스트 커버리지 100 에 도달하도록 꼼꼼하게 테스트를 작성하였습니다.

---

## Future Work

#### Cache 적용
현재 구현상 동일한 Stock Symbol(예를 들어 GOOG) 반복해서 쿼리를 하게 되면 전체 비즈니스 로직을 매번 계속 수행하게 됩니다.
이는 하루 단위로 데이터가 변하는 것을 고려했을 때, 성능상 비효율을 초래하기 때문에 Cache 를 도입하고 싶었습니다.

하지만 시간상 적용하지 못하였고, 고민해 본 아이디어를 남겨보려고 합니다.

in-memory cache 를 적용하여 동일 쿼리에 대한 연산을 반복적으로 수행하지 않도록 하기 위해 솔루션을 알아보고 있었습니다.

만약 DB 를 연동한다면 Apache Ignite 와 같은 솔루션을, 연동하지 않는다면 Redis 를 생각해볼 수 있을 것 같습니다.

고민해야할 캐시 정책으로는 문제 제한사항이 "180 days" 이기 때문에, 
하루가 지나게 된 경우 맨 마지막 데이터를 pop 하고 새로운 한 데이터를 push 해야 하는부분이 있을 것 같습니다. 

---