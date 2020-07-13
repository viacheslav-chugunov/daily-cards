package io.dailycards.tools.db

import io.dailycards.R

class Store(private val db: DB) {
    private val prefix = "[%s] ".format(db.context.getString(R.string.store_tab))
    private val description = db.context.getString(R.string.system_set)

    fun init() {
        card1(); card2(); card3(); card4()
    }

    private fun card1() {
        val cardContent = listOf(
            "Soccer" to "Футбол",
            "Competition" to "Соревнование",
            "Sweat" to "Пот",
            "Ski" to "Лыжа",
            "Horizontal bar" to "Перекладина",
            "Dumbbell" to "Гантеля",
            "Jump rope" to "Скакалка",
            "Endurance" to "Выносливость",
            "Skate" to "Конёк",
            "Warm-up" to "Разминка",
            "Fatigue" to "Усталость",
            "Barbell" to "Штанга",
            "Jog" to "Пробежка",
            "Push-ups" to "Отжимания",
            "Cup" to "Кубок",
            "Weight" to "Гиря",
            "Stop watch" to "Секундомер",
            "Fencing" to "Фехтование",
            "Pulling up" to "Подтягивание",
            "Gym" to "Спортзал",
            "Mountaineering" to "Альпинизм",
            "Club" to "Клюшка",
            "Pennant" to "Вымпел",
            "Squat" to "Приседание",
            "Rock-climbing" to "Скалолазание",
            "Washer" to "Шайба",
            "Rope" to "Канат",
            "Stretching" to "Растяжка",
            "Somersault" to "Кувырок"
        )
        db.insertToStore(prefix + "Спорт", description, cardContent)
    }

    private fun card2() {
        val cardContent = listOf(
            "Meat" to "Мясо",
            "Water" to "Вода",
            "Bread" to "Хлеб",
            "Chicken" to "Курица",
            "Pasta" to "Макароны",
            "Porridge" to "Каша",
            "Dumplings" to "Пельмени",
            "Sandwich" to "Бутерброд",
            "Apple" to "Яблоко",
            "Rice" to "Рис",
            "Cheese" to "Сыр",
            "Sausage" to "Колбаса",
            "Pilaf" to "Плов",
            "Oil" to "Масло",
            "Pancakes" to "Блины",
            "Mushrooms" to "Грибы",
            "Mashed potatoes" to "Пюре",
            "Pie" to "Пирог",
            "Juice" to "Сок",
            "Strawberry" to "Клубника",
            "Tomato" to "Помидор",
            "Cheesecake" to "Ватрушка",
            "Sour cream" to "Сметана",
            "Pork" to "Свинина",
            "Peas" to "Горошек",
            "Fish" to "Рыба",
            "Fat" to "Сало",
            "Egg" to "Яйцо",
            "Noodles" to "Лапша",
            "Bean" to "Боб",
            "Stuffed cabbage" to "Голубец",
            "Dough" to "Тесто",
            "Bun" to "Булочка",
            "Persimmon" to "Хурма",
            "Cookie" to "Печенье",
            "Cabbage" to "Капуста",
            "Pear" to "Груша",
            "Parsley" to "Петрушка",
            "Crackers" to "Сухари",
            "Halvah" to "Халва",
            "Spice" to "Пряность",
            "Partridge" to "Куропатка",
            "Ham" to "Ветчина",
            "Cottage cheese" to "Творог",
            "Cancer" to "Рак",
            "Ragout" to "Рагу",
            "Garlic" to "Чеснок",
            "Cheesecakes" to "Сырники",
            "Watermelon" to "Арбуз"
        )
        db.insertToStore(prefix + "Еда", description, cardContent)
    }

    private fun card3() {
        val cardContent = listOf(
            "Employee" to "Сотрудник",
            "Rent" to "Аренда",
            "Headquarters" to "Штаб",
            "Salary" to "Зарплата",
            "Team" to "Команда",
            "Stuff" to "Штат",
            "Warehouse" to "Склад",
            "Bulletin board" to "Доска объявлений",
            "Expert" to "Специалист",
            "Contract" to "Договор",
            "Insurance" to "Страхование",
            "Management" to "Управление",
            "Negotiation" to "Переговоры",
            "Conversation" to "Разговор",
            "Income" to "Доход",
            "Position" to "Должность",
            "Employment" to "Занятость",
            "Grief" to "Печаль"
        )
        db.insertToStore(prefix + "Офис", description, cardContent)
    }

    private fun card4() {
        val cardContent = listOf(
            "Rest" to "Отдых",
            "Trip" to "Поездка",
            "Plane" to "Самолёт",
            "Train" to "Поезд",
            "Road" to "Дорога",
            "Adventure" to "Приключение",
            "Ship" to "Корабль",
            "Backpack" to "Рюкзак",
            "Suitcase" to "Чемодан",
            "Wandering" to "Странствие",
            "Tent" to "Палатка",
            "Permit" to "Путёвка",
            "Abroad" to "Заграница",
            "Traveler" to "Путник",
            "Route" to "Маршрут",
            "Continent" to "Материк",
            "Movement" to "Движение",
            "Mountain" to "Гора",
            "Map" to "Карта",
            "Railroad" to "Железная дорога",
            "Resort" to "Курорт",
            "Departure" to "Выезд",
            "Nomad" to "Кочевник",
            "Hotel" to "Гостиница",
            "Nature" to "Природа",
            "Tickets" to "Билеты",
            "Vacation" to "Каникулы",
            "Highway" to "Шоссе",
            "Vessel" to "Судно"
        )
        db.insertToStore(prefix + "Туризм", description, cardContent)
    }

    private fun card5() {
        val cardContent = listOf(
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to "",
            "" to ""
        )
        db.insertToStore(prefix + "", description, cardContent)
    }
}
