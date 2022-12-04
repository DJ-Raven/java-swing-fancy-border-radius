# java-swing-fancy-border-radius
### Simple
```java
FancyBorderRadius border = new FancyBorderRadius(width, height, "40% 60% 60% 40% / 60% 30% 70% 40%");
Shape shape=border.getShape();
```
### Animation
```java
FancyAnimation fancyAnimation = new FancyAnimation("40% 60% 60% 40% / 60% 30% 70% 40%", "40% 60%");
FancyBorderRadius border = new FancyBorderRadius(width, height, fancyAnimation.getAnimate(animate));
Shape shape=border.getShape();
```
Border Generate
https://9elements.github.io/fancy-border-radius/

![2022-12-05_020351](https://user-images.githubusercontent.com/58245926/205510148-e87ce1c7-a6b7-4637-b876-80c294deadd4.png)
