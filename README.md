In this project, I used Jetpack dependency Inject Dagger Hilt with the architecture MVVM and Coroutines. The Api is called with Retrofil with dynamic Endpoint depending on which lotteries to show. The app shows the results of Lottery and the dates and Jackpots of next draws. I used ViewBinding technique and LiveData. I made architecture for the states of Error Internet connection failure, Server failure and Loading state.

SCREENSHOTS

Loading 
![Screenshot_20230303_023027](https://user-images.githubusercontent.com/20923486/222611731-183432f4-f56c-4247-9110-c702977f8909.png) 

RecyclerView filled
![Screenshot_20230303_023127](https://user-images.githubusercontent.com/20923486/222611771-a6ec1afc-58a1-48fd-93e5-883c3ee3173f.png)

Query many Lotteries in URL with endpoint like “lottery1, lottery2, lottery3…”
![Screenshot_20230303_023228](https://user-images.githubusercontent.com/20923486/222611805-75a8a1c8-a588-4ef3-a2da-e582868c60a7.png)

Calling dynamic Endpoint in this format: /aggregated/6aus49,eurojackpot,spiel77
![Screenshot_20230320_103843](https://user-images.githubusercontent.com/20923486/226301644-f9b4a1a2-d1f9-40ac-9916-de34cfe666b8.png)

Clean code handling states and errors
![Screenshot_20230303_023306](https://user-images.githubusercontent.com/20923486/222611847-376fcf3c-a7b5-4410-90fc-e7c5d0a90175.png)

Dialog on Item click and open in web-browser
![Screenshot_20230303_023433](https://user-images.githubusercontent.com/20923486/222611882-fad4dfdf-5c75-4cc0-a8ef-f9778da39b9c.png)
![Screenshot_20230303_023448](https://user-images.githubusercontent.com/20923486/222611942-894f4ff1-fcc3-429a-ace9-9988b0ab77ae.png)

