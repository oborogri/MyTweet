# 
<snippet>
  <content>
  	Project Name: MyTweet Application
A microblog/twitter like application, built and developed using Android Studio 2.2 &amp; Android 6.0 (API 23).
## License
Author and owner: Grigore Oboroceanu
## Installation
Download or clone project in GitHUb and import apk to Android Studio.
For testing the app connect to a physical device with external memory storage. Data is persisted on external memory card. 
##Source tree
The repo source tree comprises two main branches: master and dev, and several branches for each of the app main features. 
The final changes are merged into dev and master and tagged as releases V-01 to v-06
## Development
This project comprizes two major development stages: stage one: from v-01 to v-03 and all commits prior to November 1, 2016; 
stage two: from v_03 to v-06 final and commits from November 1, 2016 to January 8, 2017. 

The scope of the project is to build the app in several iterations and consume a public API from MyTweet webserver. 

Link to MyTweet Hapi.js web server app on github: https://github.com/oborogri/MyTweet-node

## Features 
The application allows a user to create and publish on its timeline a 140-character short message. 
All messages published are displayed in Timeline view, which is users home page. The message details may be viewed in ViewPager mode which 
allows the user to swipe in readonly mode through all messages on his timeline
A user can delete one, a few or all of his messages from timeline. The message can be send by email via phone contact list.

The mobile app retrofit interface interacts with the MyTweet webserver API and keeps local data in sync with the server. Message
created or deleted are being updated and kept in sync with the server database.

## Deployment
The application web server is deployed on Heroku and to AWS Linux AMI and the database is hosted on a separate MongoDB instance on AWS.<br>  
The web server app on Amazon:              http:// 35.160.159.30:4000 <br>
The web server app on Heroku:              https://my-tweet20073381.herokuapp.com/ <br>
