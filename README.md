# 이선좌: 이미 선택된 좌석입니다

## Table of Contents

- [Introduce](#Introduce)
- [Demonstration](#demonstration)
- [Development](#development)
  - [Architecture](#architecture)
  - [Project-setup](#Project-setup)
- [Troubleshooting](#Troubleshooting)
- [About](#about)

## Introduce

**이선좌** 프로젝트의 자바 기반 스프링 부트를 활용한 백엔드 어플리케이션입니다. 자세한 내용은 WIKI를 참조해주세요. (WIKI는 추후에 추가 예정입니다.)

## Demonstraion(AWS 비용 문제로 운영중단)
<del>http://selected-seat.shop</del>

## Development

### Architecture
![architecture](https://github.com/Selected-Seat/Select-Seat/assets/65538799/52c0e5cc-7116-4b75-b796-93984950db4e)

### Project-setup
<details>
  <summary>JDK and Frameworks</summary>

  - **JDK**: 17
  - **SpringBoot**: 3.2.x
  - **SpringBatch**: 5.1.x
  - **SpringSecurity**: 3.2.x
  - **SpringWebFlux**: 3.2.x
  - **ElasticSearch**: 8.11.x
</details>

<details>
  <summary>Database</summary>

  - **MongoDB**: 4.2.x
  - **MySQL**: 8.1.x
  - **Redis**: 7.0.x
</details>

<details>
  <summary>Infrastructure</summary>

  - **aws ECS**
  - **aws ECR**
  - **aws Route53**
  - **aws EC2**
  - **aws S3**
  - **aws VPC**
  - **aws SES**
  - **aws RDS**
  - **aws ElastiCache**
  - **aws IAM**
  - **aws ALB**
  - **aws NLB**
  - **aws AutoScailing**
  - **docker**: 23.0.x
  - **docker-compose**: 2.17.x
  - **jenkins**: 2.426.x
</details>

<details>
  <summary>Monitoring</summary>

  - **prometheus**: 2.51.x
  - **grafana**: 10.4.x
  - **Kibana**: 8.11.x
  - **aws cloudwatch**
</details>

<details>
  <summary>Frontend</summary>

  - **Vue**: 3.4.x
  - **Vue Router**: 4.3.0
  - **Node**: 21.7.x
  - **Pinia**: 2.1.x
  - **Bootstrap**: 5.3.x
  - **axios**: 1.6.x
  - **thymeleaf**: 3.1.x
</details>

<details>
  <summary>Load Test</summary>

  - **Vegeta**
  - **Jmeter**
</details>

## Technical Decision && Troubleshooting

### [ElasticSearch] 검색 속도 개선을 위한 Elasticsearch도입
---
  | 고민  
  콘서트를 검색하는 사용자는 정확한 콘서트의 네이밍을 아는 경우가 흔치 않다. 관련된 키워드 혹은 출연진의 이름으로 검색하는 경우가 많다. 프로젝트의 DB구조에서 가수의 정보 혹은 지역, 카테고리 등등으로 검색을 하게되는 경우 최대 5개 이상의 테이블을 조인해 검색하게 된다. 이후 추가된 데이터를 검색 필터로 추가한다면 성능은 더 느려질것을 예상할 수 있었다. 또한 도메인 특성상 특정 시간에 검색이 몰릴 것을 예상하였고 속도를 개선하는 방법을 고민하던 중 Document형태로 데이터를 저장할 수 있고, 역인덱스 구조로 저장되어 빠른 검색이 가능한 Elasticsearch를 도입하게 되었다.

  | 성능 비교  
  <img width="1404" alt="image" src="https://github.com/gunnu3226/TABA_Project/assets/139452702/775c0e0e-de12-47e5-b73d-be54e88e7e14">
  배포 환경에서 초당 검색 요청 횟수를 늘려가면서 Elasticsearch를 통한 검색과 쿼리로 DB에 검색하는 속도를 비교해보았다. 위 표에서 알 수 있듯이 트래픽이 증가할 수록 속도 차이는 벌어졌고, 초당 400건의 검색을 요청했을 때 검색속도는 약90%(5969ms -> 34ms)이상 개선된 것을 확인할 수 있다.

<br>

### [Queuing system] 대용량 트래픽을 처리하기 위한 Webflux
---
  | 고민  
  티켓팅은 순간 접속자가 많고 대용량의 트래픽이 몰리게 된다. 이때 Blocking 방식의 톰캣을 기반으로 하는 Spring MVC는 요청에 대해 하나의 스레드를 할당하는 방식으로 동작하게 된다. 이는 대량의 트래픽을 빠르게 처리하는 것에는 적합하지 않다.

  | 해결  
  대량의 트래픽을 보다 빠르고 안정적으로 받을 수 있는 서버가 필요하였고, Non-Blocking Netty 기반의 Webflux를 선택하게 되었다.
  로컬에서 부하 테스트를 진행했다.

  요청: 2백만 건에 대한 조회 요청

  | Target | Samples | Error % | Throughput (/sec) |
  | --- | --- | --- | --- |
  | Spring MVC | 2000000 | 0.0 | 16177/sec |
  | Spring Webflux | 2000000 | 0.0 | 42787/sec |

  결과를 통해 Spring Webflux가 Spring MVC 대비 약 3배 이상 높은 처리율을 가진다는 것을 확인할 수 있었다.
  이러한 이유를 바탕으로 Webflux를 대기열 시스템의 메인 서버로 선택하였다.

<br>

### [Lock] 티켓팅 좌석 중복 예매 방지를 위해 레디스 사용
---
  | 문제  
  실시간 예매 시스템에서 선점된 자리에 대한 동시성 제어를 분산락으로 구현했다. 하지만 분산 락으로 구현하니 사용자가 예매 프로세스를 모두 마치고 이탈하면 락이 풀려버려 DB에는 이미 예매가 완료된 좌석이지만 다른 사용자가 선택이 가능한 경우가 발생했다. 저희는 해당 콘서트 예매가 끝날 때 까지 락이 유지되어야 했다.

  | 해결  
  배치 작업을 통해 예매가 시작되기 전 콘서트 정보와 좌석을 기반으로한 key와 선점 여부에 대한 value를 Hash구조로 레디스에 올려둔다. 예매가 시작되면 선택한 좌석의 value를 true로 수정하여 좌석이 이 후 다른 사용자는 선택하지 못하도록 구현했다. 그리고 취소표를 위해서 해당 좌석에 대한 락을 생성하여 레디스에 저장해 두었고, 자정을 기준으로 락이 해제될 수 있도록 TTL을 적용해 두었다. 마지막으로 레디스에 대용량의 키를 저장하는 것은 메모리 누수로 이어질 수 있기 때문에, 만료된 공연의 좌석키를 삭제하는 배치 처리를 구현하여 스케줄러로 시동시켰다.
![test](https://github.com/gunnu3226/TABA_Project/assets/139452702/4dc6e722-508d-4fa0-9749-02199e289970)


## About

Credit to @Selected-Seat/selected-seat : @Dittttto, @gunnu3226, @RamuneOrch and @sonjh919

