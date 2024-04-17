NewsApp Demo with api "https://newsapi.org/v2/".

MVVM architecture is used.


To store data locally room database is used.



Flow of app  : 
-> when we open app it will show list of latest news but if we want to search any other topic news we can simply write topic name in given field and click on button it will shw the list of given topic news

-> when we clear the field it will automatically clear the list and give list of latest news 

-> if internet is available it will get data from server.

-> if internet is ot available it will show the list of last searched data which is stored as cache

-> pagination is used to get the response faster
