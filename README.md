# MovieDbFragments
 
* The project requires an Authentication key to run. The key is injected via the following files in the root directory as `THE_MOVIE_DB_AUTH_TOKEN={your_api_key}`:
  * secrets.properties
  * secrets.defaults.properties
 
You can get the api key from (or just ask me):
https://developer.themoviedb.org/reference/intro/getting-started

#
I developed this project during the summer to explore what’s new and to implement the same set of features using both Fragments and Jetpack Compose, then compare them.

# Here are a few important notes to keep in mind when evaluating this project: # 
* The project is relatively small, so I’ve opted for a single-module setup.
* I didn't use version catalogs because, for a single-module project, it felt like over-engineering. However, I do understand its benefits in multi-module projects.
* When I first implemented this project, Picasso was not yet deprecated (it was officially deprecated about two months ago).
* The “Search” button is purely visual and currently unimplemented.

# Code Style Notes # 
(These can be adjusted depending on the team’s decisions)
* Two data classes contain minor logic via the getImageUrl() function. It encapsulates the field (no developer needs to access it) and makes it easier to get the correct URL. 
  * Alternatively, one could use an extension function, domain model, or other solutions, depending on the team’s preferences.
* The formatting functions in MovieDetailsViewModel could be moved to a separate helper class. However, I usually keep them in the ViewModel if they’re only required by that specific Fragment and the ViewModel itself is minimal — this helps avoid unnecessary complexity (no over-engineering).
