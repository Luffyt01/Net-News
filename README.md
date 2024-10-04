Overview of the App:
The Android app delivers the latest news articles to users by fetching data from a remote server via an API in JSON format. It offers a personalized experience by allowing users to search for news articles on various topics, view their search and browsing history, and bookmark their favorite articles for later reading.

Key Features:
**Fetching News via API **

    The app is integrated with a news API that serves articles in a structured JSON format.
    It fetches real-time news updates across different categories (like politics, technology, sports, etc.).
    The data includes the article title, description, image, source, and publication date.
    
** Search Functionality **

    Users can search for specific topics or keywords, such as "climate change" or "latest tech trends."
    The app displays relevant news articles based on the search input.
    Search results are dynamically fetched from the server to ensure up-to-date information.
    
** History Tracking **

    The app keeps track of articles the user has previously viewed.
    A "History" section shows the user a list of articles they’ve interacted with, making it easy to revisit previous news items.
    The history is saved locally on the device, possibly using Room or Shared Preferences for storage.
    
** Bookmarking Articles **

    Users can save articles for later reading by bookmarking them.
    A "Bookmark" section allows them to view all their saved articles in one place.
    Bookmarks are stored locally on the device using a persistent storage solution like Room Database or Firebase (in case cloud syncing is needed).
  

