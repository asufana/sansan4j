
# Sansan4J

Sansan BizCard API for Java8.

## 使い方

### 1. Maven Repository

```xml
<dependency>
  <groupId>com.github.asufana</groupId>
  <artifactId>sansan4j</artifactId>
  <version>1.0</version>
</dependency>
```

### 2. APIクライアントインスタンスの生成

```java
// 環境変数 SANSAN_API_KEY にAPIキーを登録した上でAPIクライアントインスタンス取得
SansanApiClient api = new SansanApiClient(ApiKey.get());

//あるいはAPIキー文字列を引数で渡しAPIクライアントインスタンス取得
SansanApiClient api = new SansanApiClient(YOUR_API_KEY_STRING);
```

### 3. リクエストオブジェクトの生成

```java
BizCardsRequest request = BizCardsRequest
                                                 .builder()
                                                 .registeredFrom("20160401")
                                                 .registeredTo("20160402")
                                                 .range(RangeParam.All)
                                                 .build();
```

### 4. リクエスト送信とレスポンス処理

```java
ResponseModel<BizCards, BizCard> cards = api.request(request);
List<BizCard> results = cards.results();
```

あるいは取得数を指定して、100件以上を同時に取得する。

```java
Integer limit = 1000;
ResponseModel<BizCards, BizCard> cards = api.request(BizCardsRequest.SINCE_LASTWEEK, limit);
```

## レスポンスの処理

```java
ResponseModel<BizCards, BizCard> cards = api.request(request);

//結果リスト取得
List<BizCard> results = cards.results();

//Java8 Stream API
cards.stream()...
cards.map(BizCard::email)...
cards.filter(card -> card.email() != null)...
cards.forEach(System.out::println);
```

## リクエストの生成

### 名刺取得リクエスト（期間指定）

```java
BizCardsRequest request = BizCardsRequest.builder()
   .registeredFrom("20160401") //名刺登録日時（省略時：今日）
   .registeredTo("20160402") //名刺登録日時（省略時：明日）
   .range(RangeParam.All) //所有者の範囲（省略時：All）
   .limit(100) //取得上限数（省略時：100）
   .offset(0) //取得開始位置（省略時：0）
   .status(StatusParam.Completed) //名刺状態（省略時：Processing+Completed+Unreadable）
   .statuses(Arrays.asList(StatusParam.Completed, StatusParam.Processing)) //名刺状態の複数指定
   .build();
```

ショートカット

```java
//今日登録された名刺一覧を検索する
BizCardsRequest request = BizCardsRequest.TODAY;
//昨日登録された名刺一覧を検索する
BizCardsRequest request = BizCardsRequest.YESTERDAY;
//昨日から今日までに登録された名刺一覧を検索する
BizCardsRequest request = BizCardsRequest.SINCE_YESTERDAY;
//先週から今日までに登録された名刺一覧を検索する
BizCardsRequest request = BizCardsRequest.SINCE_LASTWEEK;
```

### 名刺取得リクエスト（条件指定）

```java
BizCardsRequest request = BizCardsRequest.builder()
   .company("xxx") //会社名
   .name("xxx") //氏名
   .email("xxx") //メールアドレス
   .tel("xxx") //電話番号
   .mobile("xxx") //携帯番号
   .tag("xxx") //タグID
   .tags(Arrays.asList("xxx", "xxx")) //タグIDの複数指定
   .range(RangeParam.All)
   .limit(100)
   .offset(0)
   .statuses(Arrays.asList(StatusParam.Completed, StatusParam.Processing))
   .build();
```

### 人物取得リクエスト

```java
PersonRequest request = new PersonRequest("xxx"); //人物ID
```

### タグ取得リクエスト

```java
TagsRequest request = TagsRequest
    .builder()
    .range(RangeParam.All) 
    .type(TypeParam.Shared) //タグ種別（省略時：private+public+shared）
    .types(Arrays.asList(TypeParam.Shared, TypeParam.Public)) //タグ種別の複数指定
    .limit(100)
    .offset(0)
    .build();
```


## Try/Eitherラッピング

APIの多段呼び出し時のエラーハンドリング

```java
SansanApiClient api = new SansanApiClient(ApiKey.get());

//タグ抽出API
String searchTagName = "Import";
TagsRequest tagsRequest = TagsRequest.ALL;
Try<ResponseModel<Tags, Tag>> tagsResponse = api.requestWrappedTry(tagsRequest);
Try<Tag> tagResult = tagsResponse.mapTry(res -> res.filter(tag -> tag.name()
        .startsWith(searchTagName))
        .findAny()
        .orElseThrow(() -> new SansanApiClientException("指定タグが見つかりません：" + searchTagName)));

//名刺抽出API
Try<List<String>> companies = tagResult.flatMap(tag -> {
    BizCardsRequest cardsRequest = BizCardsRequest.builder()
            .tag(tag.id())
            .build();
    Try<ResponseModel<BizCards, BizCard>> cardsResponse = api.requestWrappedTry(cardsRequest);
    return cardsResponse.mapTry(res -> res.filter(card -> isNotEmpty(card.prefecture()))
            .filter(card -> card.prefecture().equals("東京都"))
            .map(BizCard::companyName)
            .distinct()
            .sorted()
            .collect(Collectors.toList()));
});

//取得できたら
if (companies.toEither().isRight()) {
    List<String> tokyoCompanies = companies.toEither().right().get();
    System.out.println(tokyoCompanies);
}
//途中で例外が発生していたら
else {
    System.out.println(companies.toEither().left().get());
}
```

