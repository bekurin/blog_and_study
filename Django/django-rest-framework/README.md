# djnago rest framework
### django를 사용한 rest api를 level에 맞게 구현하는 프로젝트입니다.

- <a href="#level_1">api-level-1</a> 설명 바로가기
- <a href="#level_2">api-level-2</a> 설명 바로가기
- <a href="#level_3">api-level-3</a> 설명 바로가기

---

<div name="level_1"></div>

## api-level-1
#### APIView(CBV)로 기능을 구현한 news api project 입니다.
- Journalist, Article model을 제작했습니다.
- APIView를 통해서 request method의 기능을 직접 구현한 rest api 입니다.
- HyperlinkedRelatedField를 이용하여 결과를 보다 쉽게 볼 수 있게 하였습니다.

|endpoints|content|
|-|-|
|/api/articles/|모든 Article 대한 정보를 볼 수 있습니다.|
|/api/articles/\<int:pk>\/|pk에 맞는 상세 Article 볼 수 있습니다.|
|/api/journalists/|Journalist의 정보와 함께 작성한 Article hyperlink 형태로 볼 수 있습니다.|
|/api/journalists/\<int:pk>\/|pk에 맞는 상세 Journalist를 볼 수 있습니다.|

---

<div name="level_2"></div>

## api-level-2
#### mixins를 이용하여 기능을 구현한 food-recipe api project 입니다.
- Chef, Food, FoodRecipe model을 제작했습니다.
- mixins을 사용하여 API 구현하였습니다.
- TabularInline을 사용하여 admin site에서 쉽게 편집이 가능합니다.

|endpoints|content|
|-|-|
|/api/foods/|모든 Food에 대한 정보를 볼 수 있습니다. Food에 맞는 FoodRecipe의 정보를 Hyperlink로 확인할 수 있습니다.|
|/api/fods/\<int:pk>\/|pk에 맞는 상세 Food의 정보를 볼 수 있습니다.|
|/api/food_recipe/|모든 FoodRecipe의 정보를 볼 수 있습니다.|
|/api/food_recipe/\<int:pk>\/|pk에 맞는 상세 FoodRecipe의 정보를 볼 수 있습니다.|
|/api/food_recipe/\<int:food_pk>\/recipe/|food_pk에 맞는 모든 FoodRecipe의 정보를 볼 수 있습니다.|
|/api/chef/|모든 Chef에 대한 정보를 볼 수 있습니다. Chef에 맞는 Food의 정보를 Hyperlink로 확인할 수 있습니다.|
|/api/chef/\<int:pk>\/|pk에 맞는 상세 Chef의 정보를 볼 수 있습니다.|

---

<div name="level_3"></div>

## api-level-3
#### rest-auth를 활용하여 user 로그인, 로그아웃을 구현한 user api project 입니다.
- Profile, ProfileStatus 모델을 제작하였습니다.
- rest-auth, allauth를 사용하여 회원 로그인을 구현하였습니다.
- signal을 사용하여 user 생성 시 Profile도 동시에 생성되게 하였습니다.

|endpoints|content|
|-|-|
|/api/profiles/|모든 Profile에 대한 정보를 볼 수 있습니다.|
|/api/profiles/\<int:pk>\/|pk에 맞는 Profile 정보를 볼 수 있습니다.|
|/api/status/|모든 ProfileStatus에 대한 정보를 볼 수 있습니다.|
|/api/status/\<int:pk>\/|pk에 맞는 ProfileStatus 정보를 볼 수 있습니다.|
|/api/avatar/|현재 log-in된 user의 제한된 Profile 정보를 볼 수 있습니다.|
