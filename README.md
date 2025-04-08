# EasyCommand

`EasyCommand`는 자바로 만들어 간단하게 명령어 시스템을 관리할 수 있는 명령어 프레임워크입니다.

---

## ✨ Features

- ✅ 명령어 별 설명 및 별칭(그리고 alias) 등록 가능
- ✅ 명령어 등록/해제 동적 지원
- ✅ 외부 프레임워크 또는 라이브러리 필요 없음 (pure Java)

---

## 코드 예시

### 기본:

```java
AbstractCommand hello = EasyCommandBuilder.create("hello")
    .aliases("hi", "hey")
    .description("Print hello message")
    .execute(args -> System.out.println("Hello, World!"))
    .build(true);
```
---
### 서브 명령어 포함:

```java
AbstractCommand sub = EasyCommandBuilder.create("start")
    .execute(args -> System.out.println("Game started!"))
    .build(false);

AbstractCommand root = EasyCommandBuilder.create("game")
    .addSubCommand(sub)
    .execute(args -> System.out.println("Use subcommands like 'start'"))
    .build(true);
```
또는
```java
AbstractCommand root = EasyCommandBuilder.create("game")
    .addSubCommand(
            EasyCommandBuilder.create("start")
                .execute(args -> System.out.println("Game started!"))
                .build(false)
    )
    .execute(args -> System.out.println("Use subcommands like 'start'"))
    .build(true);
```
---
### 실행:

```java
EasyCommand.executeCommand("hello");
EasyCommand.executeCommand("game start");
```

---

## 확장 프로젝트

| 프로젝트 이름 | 설명    |
|-------|-------|
| 추가 예정 | 개발 예정 |

---

## TO-DO
- [ ] 비동기적 명령어 실행처리
- [ ] `/help` 자동 생성
- [ ] JavaDoc 및 각 메서드의 설명

---

## License

MIT License. 자유롭게 사용하시면서도, 출처는 남겨주세요!

---

## Maintainer

- GitHub: [박정민(PJM03)](https://github.com/pjm03)
- Email: pjm5129@naver.com
