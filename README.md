# About

* This application allows you to upload and download files from your Dropbox App provided you have the access token of the App.
* The Rest enpoints exposed here can be accessed via PostMan.

## Requirements to access the application

* Create a new Dropbox App in [App Console](https://www.dropbox.com/developers/apps).
* Then Generate the access token for your App. (We will use it later to access the APIs)

## To run the application:
```
  1) mvn clean install
  2) mvn spring-boot:run
```
* Then access the application at `http://localhost:8080`

* Use the postman collection provided

## It exposes following Rest endpoints

* 1) API to Register your Dropbox App Access token with this application
     ```
     HTTPMethod: POST
     URI: /register?token=<your_dropbox_app_access_token>
     ```
     This will store the access token in the HTTP session.
     
     You will need to pass this token as a header for further operations
     
* 2) API to get files and folders in a given folder
     ```
     HTTPMethod: GET
     URI: /folder?folder=<absolute_path_of_folder>
     Headers: Token=<your_dropbox_app_access_token>
     ```
     This will get all the folders and files inside the given folder(includes inner folders as well)
     
     You will get 401 if token is not provided or if HTTP session gets expired
     
* 3) API to download a file from your dropbox folder
     ```
     HTTPMethod: GET
     URI: /download?filePath=<absolute_path_of_file_with_extension>
     Headers: Token=<your_dropbox_app_access_token>
     ```
     This will download the file to your local FileSystem. Use Send and Download option in Postman.
     
     You will get 401 if token is not provided or if HTTP session gets expired
     
* 4) API to upload a file to your dropbox folder
     ```
     HTTPMethod: POST
     URI: /upload?folder=<absolute_path_of_folder>
     Headers: Token=<your_dropbox_app_access_token>
     ```
     Provide body as form-data with file as key and choose any file from your local FileSystem.
     
     This will upload the given file to the given folder in your dropbox.
     
     You will get 401 if token is not provided or if HTTP session gets expired
     
## Future Development
* To develop a User Interface to access these APIs so that user can view, upload and download the folders and files from there
* To enable logging into the application through Dropbox OAuth.
* To configure the application so as to use any document provider as required.
