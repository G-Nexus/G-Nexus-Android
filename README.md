# G-Nexus-Android

## Project Structure

```
source/
├── data/
│    ├── api                 <-- Retrofit 接口 + DTO -->
│    ├── db                  <-- Room 数据库 + Dao + Entity -->
│    ├── mapper              <-- DTO ↔ Entity ↔ Domain 转换 -->
│    ├── paging              <-- PagingSource（网络或本地分页）-->
│    └── repository          <-- RepositoryImpl + safeApiCall -->
├── domain/
│ ├── model               <-- Domain 层实体（Game.kt）-->
│ └──repository          <-- Repository 接口 -->
├── ui/
│    ├── viewmodel           <-- GameViewModel -->
│    └── GameListScreen.kt   <-- Compose UI -->
├── utils
│     ├── Resource.kt         <-- 封装网络状态 -->
│     └── ApiHelper.kt        <-- safeApiCall -->
```
