# 🧠 QuizApp — Mantiqiy Testlar

**Mantiq, zukkolik va topqirligingizni sinaydigan zamonaviy Android viktorina ilovasi**

![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)
![Language](https://img.shields.io/badge/Language-Java-ED8B00?logo=openjdk&logoColor=white)
![Architecture](https://img.shields.io/badge/Architecture-MVP-blue)
![Min SDK](https://img.shields.io/badge/minSdk-24-orange)
![Target SDK](https://img.shields.io/badge/targetSdk-36-brightgreen)
![Version](https://img.shields.io/badge/version-1.0-informational)

</div>

---

## 📖 Loyiha haqida

**QuizApp** — o'zbek tilidagi mantiqiy savollar to'plamiga asoslangan Android ilovasi. Foydalanuvchi turli kategoriyalardagi testlarni yechadi, har bir to'g'ri javob uchun 💎 **olmos** yutadi, qiyin savollarda olmos evaziga **maslahat (hint)** oladi va natijasini do'stlari bilan ulashadi.

Ilova **MVP (Model–View–Presenter)** arxitekturasida yozilgan bo'lib, kod qatlamlari aniq ajratilgan va kengaytirish oson.

## 📸 Skrinshotlar

<div align="center">

<table>
  <tr>
    <td align="center"><img src="https://github.com/user-attachments/assets/420c7bd7-bfec-47a7-bd95-5e7c2e0173ca" width="200" alt="Skrinshot 1"/></td>
    <td align="center"><img src="https://github.com/user-attachments/assets/b898a1e4-ada2-42e3-9ecf-5c69023be0e7" width="200" alt="Skrinshot 2"/></td>
    <td align="center"><img src="https://github.com/user-attachments/assets/999511e4-8b15-41c5-b553-d0a1b28f0a25" width="200" alt="Skrinshot 3"/></td>
    <td align="center"><img src="https://github.com/user-attachments/assets/c0d20b30-6945-499f-8196-0afcd175ece8" width="200" alt="Skrinshot 4"/></td>
  </tr>
</table>

</div>

## ✨ Asosiy imkoniyatlar

- 🗂 **5 ta kategoriya** — har birida 10 tadan savol (jami **50 ta savol**)
- 🔍 **Qidiruv** — kategoriyalarni tezda topish uchun filtr
- ▶️ **Davom ettirish (Resume)** — yakunlanmagan testni to'xtagan joydan davom ettirish
- ⏭ **Navigatsiya** — oldingi savolga qaytish, savolni tashlab o'tish va keyin qaytish
- 💡 **Maslahat tizimi** — 50 💎 evaziga to'g'ri javobni ko'rsatadi
- 💎 **Olmos tizimi** — har bir to'g'ri javob uchun +10 💎, barcha javob to'g'ri bo'lsa +20 💎 bonus
- 📊 **Doiraviy natija ko'rsatkichi** — natijaga qarab rangi o'zgaradi (yashil / sariq / qizil)
- 🕘 **So'nggi faoliyat** — oxirgi yechilgan kategoriyalar va ballar ro'yxati
- 📤 **Natijani ulashish** — Telegram va boshqa ilovalar orqali
- 💾 **Lokal saqlash** — barcha ma'lumotlar `SharedPreferences` orqali qurilmada saqlanadi (internet shart emas)
- 🌙 Yorug' va tungi (dark) mavzu qo'llab-quvvatlanadi

## 🗂 Kategoriyalar

| # | Kategoriya | Tavsif |
|---|------------|--------|
| 1 | 🧩 **Mantiq** | Mantiqiy fikrlash savollari |
| 2 | 🧠 **Boshqotirma** | Boshni qotiruvchi topshiriqlar |
| 3 | 💡 **Zukkolik** | Zukkolik va topqirlikni sinash |
| 4 | ⚡ **Topqirlik** | Tezkor fikrlash savollari |
| 5 | ❓ **Topishmoqlar** | Xalq topishmoqlari |

## 🏗 Arxitektura

Loyiha **MVP** namunasida qurilgan: har bir ekran uchun `Contract`, `View` va `Presenter` mavjud.

```
com.abdullojon.quizapp
├── start/                  # Bosh ekran
│   ├── StartContract.java
│   ├── view/StartActivity.java
│   └── presenter/StartPresenterImpl.java
├── quiz/                   # Test ekrani
│   ├── QuizContract.java
│   ├── view/QuizActivity.java
│   └── presenter/QuizPresenterImpl.java
├── data/
│   ├── UserPreferences.java        # Foydalanuvchi, olmos va ballar (SharedPreferences)
│   ├── models/QuizData.java        # Savol modeli
│   └── repositories/QuizModelImpl.java
├── view/CircularScoreView.java     # Custom doiraviy ball ko'rsatkichi
└── ResultActivity.java             # Natija ekrani
```

## 🛠 Texnologiyalar

| Texnologiya | Qo'llanilishi |
|-------------|---------------|
| **Java 11** | Asosiy dasturlash tili |
| **Android SDK** (min 24 / target 36) | Platforma |
| **MVP** | Arxitektura namunasi |
| **Material Components** | UI komponentlari |
| **AppCompat & ConstraintLayout** | Layout va moslashuvchanlik |
| **SharedPreferences** | Lokal ma'lumotlarni saqlash |
| **Custom View** | `CircularScoreView` — natija ko'rsatkichi |
| **Gradle (Kotlin DSL)** + Version Catalog | Build tizimi |

## 🎮 Qanday o'ynaladi?

1. Bosh ekranda kategoriyani tanlang (yoki qidiruvdan foydalaning)
2. Savolga javob variantlaridan birini belgilang
3. Qiyin bo'lsa — 💡 tugmasi orqali 50 💎 evaziga maslahat oling
4. Savolni **Tashlab o'tish** yoki **Oldingisi** tugmalari bilan boshqaring
5. Yakunlang va natijangiz hamda yutgan olmoslaringizni ko'ring
6. Natijani do'stlaringiz bilan ulashing 📤

## 👨‍💻 Muallif

**Abdullojon Farmonov**  
📧 [farmonovabdullojon04@gmail.com](mailto:farmonovabdullojon04@gmail.com)

---

<div align="center">

⭐ Loyiha yoqqan bo'lsa, **star** bosishni unutmang!

</div>
