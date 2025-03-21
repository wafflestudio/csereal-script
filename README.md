# csereal-script
## 목적
- csreal 서버 백엔드에 마이그레이션 혹은 스크립트 작업이 필요하고, 단순 sql로 처리가 어려운 케이스를 처리하기 위한 프로젝트

## 구조
- 기본적으로 gradle 멀티 모듈로 구성되어 있습니다.
- generated
    - csereal-server와 관련되어 해당 환경에 맞는 코드를 생성하는 모듈입니다.
    - jooq: csereal-server의 db 스키마를 기반으로 sql builder, dao, dto 코드를 생성합니다.
- scripts
    - 실제 script를 실행하기 위한 모듈입니다.
    - 공용으로 사용될 라이브러리, 코드를 포함하는 모듈입니다.
    - scripts 하위에 서브 모듈을 생성하여 각각의 스크립트를 관리합니다.

## 실행법
1. `generated/build.gradle.kts`을 수정, `jooqCodegen`을 통하여 해당 환경에 맞는 코드 생성
2. `scripts` 하위에 새로운 gradle submodule을 생성
3. `setting.gradle.kts` 하위에 생성한 submodule을 추가
4. 코드 작성 후 해당 script의 main 함수를 실행
   - 이 때 `mustGetConnection`에서 db connection 정보를 수정했는지 확인

## TODO
- API 호출 통한 스크립트 지원 위해 swagger codegen 추가하기
- jooq codegen, db connection 정보 env로부터 읽을 수 있도록 수정하기