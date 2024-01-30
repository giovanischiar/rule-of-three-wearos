<h1 align="center">
  <img src="readme-res/ic_launcher.svg" width="100" height="100"><br>
  Rule Of 3
</h1>

<p align="center">
  <strong>Calculate the famous rule of three in your wrist!</strong><br>
  This tiny wearOS app lets you use the <a href="https://en.wikipedia.org/wiki/Cross-multiplication#Rule_of_three">rule of three</a> to calculate how the 4th number will be by inputing the prior 3 numbers
</p>

- [Use Cases](#use-cases)
  - [Calculating the 4th number](#calculating-the-4th-number)
  - [Seeing the Result](#seeing-the-result-diaglog)
  - [Seeing the History](#seeing-the-history)
- [Technologies](#technologies)
- [Challenges](#challenges)
- [Diagrams](#diagrams)
  -[Package `io.schiar.ruleofthree`](#package-ioschiarruleofthree)

## Use Cases

### Calculating the 4th number
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Screenshot&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|Description|
|:-:|:-:|
|<img src="readme-res/screenshots/cross-multiplication-screen-empty-without-history.png" width="384" height="384">|This is how the app looks like when you open it. To add the numbers click on the squares|
|<img src="readme-res/screenshots/changing-the-unknown-position.gif" width="384" height="384">|You can change the unknown position anytime by long clicking on the input you want to change to the unknown|
|<img src="readme-res/screenshots/inputting-numbers.gif" width="384" height="384">|When you click on the square the input number appear. You can input any number, backspace, or clear it and then click on the most right button to submit| 
|<img src="readme-res/screenshots/erasing-all-inputs.gif" width="384" height="384">|When the numbers are being input you can start over by clicking on bottom icon to erase all inputs|
|<img src="readme-res/screenshots/calculating-the-4th-number.gif" width="384" height="384">|Imagine you are cooking rice, and you saw on the nutritional table that to make 45g of raw rice is 160kcal, and you wonder how many calories would be if you cook 62g of rice. You can input "45" and "160" on the first and second square, and then you can input "62" on the third square. The Result will appear right where the "?" is|

### Seeing the Result Diaglog
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Screenshot&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|Description|
|:-:|:-:|
|<img src="readme-res/screenshots/increasing-decreasing-decimals.gif" width="384" height="384">|When you click on the results you can see the number bigger on a dialog. You can increase the precision by clicking on the bottom icon. You can also decrease once you increase it by clicking on the upper icon. If the options are not appearing that means the number is at its maximum precision|

### Seeing the History
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Screenshot&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|Description|
|:-:|:-:|
|<img src="readme-res/screenshots/seeing-the-history.gif" width="384" height="384">|Once you've done at least one calculation, the history button will appear at the center right letting you to see all the calculations you've done. You can edit the inputs by pressing it just like the on the cross multiplication screen, delete the calculation by clicking on the right icon, go back to the cross multiplication screen by clicking on icon on the left, or delete all of them by clicking on the top icon|

# Technologies
|Technology|Purpose|
|:-:|:-:|
|<img src="https://3.bp.blogspot.com/-VVp3WvJvl84/X0Vu6EjYqDI/AAAAAAAAPjU/ZOMKiUlgfg8ok8DY8Hc-ocOvGdB0z86AgCLcBGAsYHQ/s1600/jetpack%2Bcompose%2Bicon_RGB.png" width="50" height="50"><br>[Jetpack Compose](https://developer.android.com/jetpack/compose)|Designing UI|
|<img src="https://4.bp.blogspot.com/-NnAkV5vpYuw/XNMYF4RtLvI/AAAAAAAAI70/kdgLm3cnTO4FB4rUC0v9smscN3zHJPlLgCLcBGAs/s1600/Jetpack_logo%2B%25282%2529.png" width="50" height="50"><br>[Room](https://developer.android.com/jetpack/androidx/releases/room)|Persistence of History and Current Cross Multipliers|
|<img src="https://github.githubassets.com/assets/GitHub-Mark-ea2971cee799.png" width="50" height="50"><br>[IconCreator](https://github.com/giovanischiar/icon-creator)|My own library that generate the application icon|

## Challenges
  - Not any particular challenge on creating this app. Although it's a very simple app, it was interesting to create a wearOS app;

## Diagrams
### Package `io.schiar.ruleofthree`
<picture>
  <source media="(prefers-color-scheme: dark)" srcset="./readme-res/diagrams/dark/io-schiar-ruleofthree-structure-diagram.dark.svg">
  <img alt="Package io.schiar.ruleofthree Diagram" src="./readme-res/diagrams/dark/io-schiar-ruleofthree-structure-diagram.light.svg">
</picture>