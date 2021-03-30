# Работа с ViewPager и диалогами

Задача
Домашнее задание:

1. Создать AppActivity, которая будет хостить все фрагменты в приложении.

2. Создать и отобразить фрагмент со вьюпейджером, в котором будет располагаться набор страниц. 
Необходимо использовать ViewPager2. Страница будет отображать статью с картинкой и текстом под ней. 

3. Создать фрагмент для страницы статьи. В качестве аргументов фрагмент должен принимать 
string-ресурс и drawable-ресурс. Используйте аннотации @StringRes, @DrawableRes.
 Разметку фрагмента создайте по своему предпочтению.

4. Создать data class модели статьи, который будет хранить string-ресурс текста статьи и 
drawable-ресурс изображения статьи.

5. Создать класс адаптера, который будет конвертировать объект класса статьи во фрагмент. 
Наследовать адаптер от FragmentStateAdapter, реализовать требуемые методы.

6. Связать адаптер со вьюпейджером во фрагменте.

7. Добавить индикатор выбранной страницы для ViewPager.

8. Добавить трансформацию страниц при перелистывании.

9. Связать ViewPager с TabLayout для отображения вкладок. У вкладки должен быть заголовок, 
который представляет из себя очень краткий заголовок статьи. 
Для TabLayout использовать подходящий tabMode.

10. Во фрагменте статьи добавить кнопку “сгенерировать событие”, по нажатию на которую будет 
появляться бейджик на вкладке случайной статьи (или инкрементироваться счетчик бейджика, 
если бейджик уже существовал). Бейджик на вкладке статьи должен исчезать при перелистывании на эту 
статью.

11. Создать enum ArticleTag для тегов статей. Например: новости, технологии, политика.

12. В класс статьи добавить поле tags. Тип поля - List<ArticleTag>

13. На фрагмент со вьюпейджером добавить тулбар, в меню тулбара добавить кнопку фильтрации статей
 по тегам.

14. По нажатию на кнопку фильтрации отобразить диалог, в котором можно выбрать интересующие теги 
статей и применить фильтр. Реализовать диалог с помощью DialogFragment.  Диалог должен поддерживать 
мультивыбор тегов. В диалоге должны быть кнопки “Применить” - применяет фильтр, “Отмена” - 
закрывает диалог без применения фильтра. При открытии диалога необходимо отобразить теги, 
которые были выбраны раньше, для этого выбранные ранее теги необходимо передавать через аргументы.
 По умолчанию при первом открытии экрана все теги выбраны. 

15. При нажатии на кнопку применить в диалоге выбранные теги передаются в родительский фрагмент со 
вьюпейджером. В родительском фрагменте происходит фильтрация статей, которые подходят по выбранным 
тегам. Отфильтрованные статьи устанавливаются во вьюпейджер.



Рекомендации

Для добавления индикатора можно использовать библиотеку.

Примеры трансформаций можно найти в репозитории.



Пример установки отфильтрованных статей (рекомендуется вынести это в метод, который будет принимать 
список отфильтрованных статей, также вызывать этот метод при открытии экрана — передавать на вход 
методу все статьи): 

val adapter = OnboardingAdapter(filteredArticles, activity)
viewpager.adapter = adapter
indicator.setViewPager2(viewpager)


Используйте репозиторий learning_materials / android_basic
Скачайте изменения в репозитории на локальную машину.
Выполните ДЗ в папке Fragments.
Отправьте коммиты в удаленный репозиторий.