# RedditHotList
A simple Android application to demonstrate how to represent dynamic list data fetched from an endpoint in UI using Android recycler view. This application follows MVVM structural design pattern.

The app has one screen, the main screen lists all the hot list from reddit in a paginated representation and by clicking on a list item that takes you to a web browser where more details can be found.

## Technologies and Frameworks
* Programming Language
    * Kotlin
* Packages
    * Dagger - For dependency injection
    * Retrofit - A type safe Http-Client. It is an incredible easy to use library that turns your API into a Java (Kotlin) interface.
    * OkHttp - OkHttp is an HTTP client.
* Components
    * Recycler View - RecyclerView makes it easy to efficiently display large sets of data
* Architecture
    * MVVM - MVVM separates your view (i.e. Activitys and Fragments) from your business logic.


## Usage and Configuratoin

### Prerequisites
To build and run the app you need **Android Studio**(or any relavent IDE with android support) with gradle and its related needed packages installed.
