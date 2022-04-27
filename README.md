# ParticleAnimLib

This library allows developers to include complex minecraft particles animations and trails within their own spigot plugins.

## Introduction

This project is a library, not a plugin. It has to be included in other plugins to work.

## Installation

### 1. Include the dependency in your project

#### a) Using Gradle

```properties
repositories {
  maven {
    url "https://repository.lasers-enigma.eu:443/r"
  }
  maven { url 'https://jitpack.io' }
}

dependencies {
  implementation 'fr.skytale:particle-animation-lib:4.9.0'
}
```

#### b) Using Maven

```xml
<repositories>
  <repository>
    <id>lasers-enigma</id>
    <url>https://repository.lasers-enigma.eu/artifactory/r/</url>
  </repository>
</repositories>


<dependencies>
  <dependency>
      <groupId>fr.skytale</groupId>
      <artifactId>particle-animation-lib</artifactId>
      <version>4.9.0</version>
  </dependency>
</dependencies>    
```

### [OPTIONAL] Activate the Bundled testing system

In order to **try out the library features without writing a single line of code**, you may want to activate the testing system.

This testing system has been made to let users test quickly every preset of animations.

> **Do not let the testing system activated in production. Use it only to test the library.**

#### a) Add a new command to your plugin.yml

If you want to use the testing system commands (strongly recommended), you will have to add the testing system command in your plugin.yml:
[>> Copy the command from this plugin.yml example <<](https://gitlab.com/skytale_/skytale-mc/particleanimlibtest/-/blob/master/src/main/resources/plugin.yml).

#### b) Activate the testing system

When your plugin starts (in the onEnable() method), simply activate the testing system.

```java
// With the previously defined command
ParticleAnimLibTest.enable(this, Bukkit.getPluginCommand("panim"));

// Or without it (if you choose not to modify your plugin.yml):
// ParticleAnimLibTest.enable(this, null);
```

#### c) Slightly modify your pom/gradle.build

In order to make the Image Sample work, you will have to include the resources located in the "/images/" folder of this library jar into your own plugin final jar.
With gradle, this can be done using the "shadowJar" task and by adding the following to your gradle.build file :

```properties
plugins {
  id "java"
  id "com.github.johnrengelman.shadow" version "5.2.0"
}

shadowJar {
  append 'images/'
  append 'fonts/'
  append '3dmodels/'
}
```

## In-Game Testing

If you activated the testing system, you will be able to use the following features.

* Right click air:
    * it will show the currently selected animation (or the default one)
* Commands:
    * `/panim`: starts the currently selected animation (or the default one)
    * `/panim event`: Toggle the showing of the animation on right click air
    * `/panim type <AnimationType>`: Select the animation to be shown
    * `/panim showall`: Show all the animations.
    * `/panim trail`: Show the selected trail animation (or the default one) when you move
    * `/panim trail <TrailType>`: Select the trail animation to be shown

## Add animations to your plugin

### 1. Create a builder

When you create Animations or TrailAnimations, it begins with a builder.

#### You can **create an empty one**:

* Example with a cuboid particle animation
    ```java
    CuboidBuilder cuboidBuilder = new CuboidBuilder();
    ```
  [See all animation types](https://gitlab.com/skytale_/skytale-mc/particleanimlib/-/tree/master/src/main/java/fr/skytale/particleanimlib/animation/animation)


* Example with a trail animation
    ```java
    TrailBuilder trailBuilder = new TrailBuilder();
    ```

#### Or you can **create one from a preset**:

* Example with a cuboid particle animation
  ```java
  CuboidBuilder builder = (CuboidBuilder) AnimationPreset.CUBOID_ROTATING.createBuilder();
  ```
  [See all AnimationPreset](https://gitlab.com/skytale_/skytale-mc/particleanimlib/-/tree/master/src/main/java/fr/skytale/particleanimlib/animation/attribute/AnimationPreset.java)


* Example with a trail animation
  ```java
  TrailBuilder builder = TrailPreset.CIRCLE_MOVING_UP.createBuilder(myPluginMainClass);
  ```
  [See all TrailPreset](https://gitlab.com/skytale_/skytale-mc/particleanimlib/-/tree/master/src/main/java/fr/skytale/particleanimlib/trail/attribute/TrailPreset.java)

### 2. Use the builder

You can use a lot of methods to fill an empty builder or to slightly modify a preset builder.

#### Example to build a Cuboid

```java
CuboidBuilder builder=new CuboidBuilder();
        builder.setPosition(APosition.fromEntity(player));
        builder.setRotation(new Constant<>(new Vector(0,1,0)),new DoublePeriodicallyEvolvingVariable(Math.toRadians(0),Math.toRadians(1),0));
        builder.setFromLocationToFirstCorner(new VectorPeriodicallyEvolvingVariable(new Vector(-3,-3,-3),new Vector(0.05,0.1,0.05),10));
        builder.setFromLocationToSecondCorner(new VectorPeriodicallyEvolvingVariable(new Vector(3,3,3),new Vector(-0.05,-0.1,-0.05),10));
        builder.setDistanceBetweenPoints(new Constant<>(0.4));
        builder.setMainParticle(new ParticleTemplate("REDSTONE",new Color(255,170,0),null));
        builder.setTicksDuration(400);
        builder.setShowPeriod(new Constant<>(1));
        builder.setJavaPlugin(plugin);
```

#### Example to build a Trail

```java
TrailBuilder trailBuilder=new TrailBuilder();
        CircleBuilder circleBuilder=new CircleBuilder();
        circleBuilder.setRadius(new Constant<>(2.0));
        circleBuilder.setNbPoints(new Constant<>(10),true);
        circleBuilder.setPosition(APosition.fromTrail(new Constant<>(new Vector(0,0,0))));
        circleBuilder.setDirectorVectors(new Constant<>(new Vector(1,0,0)),new Constant<>(new Vector(0,0,1)));

        circleBuilder.setMainParticle(new ParticleTemplate("REDSTONE",new Color(255,170,0),null));
        circleBuilder.setTicksDuration(80);
        circleBuilder.setShowPeriod(new Constant<>(5));
        circleBuilder.setJavaPlugin(plugin);

        trailBuilder.setDuration(Duration.ofSeconds(200));
        trailBuilder.setCheckPeriod(2);
        trailBuilder.setMinPlayerToAnimationDistance(1);
        trailBuilder.setMinDistanceBetweenAnimations(2);
        trailBuilder.addAnimation(circleBuilder.getAnimation(true));
```

#### Understanding IVariable

Some of the builder methods expects IVariable as parameter.

A IVariable can be:

* A constant:
  ```java
  // Simple Integer equal to 3
  IVariable<Integer> const3 = new Constant<>(3);                                                

  // A fixed Location
  IVariable<Location> constLoc = new Constant<>(player.getLocation().clone());
  ```

* A value that changes periodically:
  ```java
  //An Integer that starts at 0 and is increased by 1 each 3 ticks
  IVariable<Integer> increasingInt = new IntegerPeriodicallyEvolvingVariable(0, 1, 3);
  
  //A vector that goes 1 block up each 2 ticks
  IVariable<Vector> vectorMovingUp = new VectorPeriodicallyEvolvingVariable(new Vector(0,0,0), new Vector(0, 1, 0), 2);
  ```

* Based on a callback (that can contain any complex equation):
  ```java
  IVariable<double> evolvingDouble = new CallbackVariable<>(iterationCount -> 0.3 + Math.sin(iterationCount) / 4));
  
  IVariable<Location> evolvingLocation = new CallbackWithPreviousValueVariable<>(player.getLocation().clone(),(iterationCount, previousValue) -> {
           if (iterationCount < 20) {
               //Doesn't move during the first second (20 ticks)
               return previousValue;
           } else if (iterationCount < 60) {
               //Then moves up during the next 2 seconds (40 ticks)
                return previousValue.add(new Vector(0, 0.05, 0));   
           } else {
               //Then moves down until the ends of the animation 
               return previousValue.add(new Vector(0, -0.05, 0));
           }
        });
  ```
  the parameter iterationCount is a value starting from 0 and increasing on each animation show iteration.

### 3. Start the animation

#### For normal animations

```java
builder.getAnimation().show();
```

> You can save the result of getAnimation() (cache it within an attribute) in order to be able to show the animation multiple times in a row.
> You can also change the parameters of the animation between each show.

#### For trails

```java
TrailTask trailTask=trailBuilder.getTrail().getTrailTask();
        trailTask.addPlayer(player);
```

> You can save the TrailTask (cache it within an attribute) in order to be able to add the trail to other players later.
> You can also change the parameters of the Trail and its animation(s) before creating another task from it.

## Animations and parameters

### Common parameters

#### position

> Where the animation will take place. It's the center of the animation.

```java
builder.setPosition(APosition position)
```

##### Exemples

> It can be a fixed location

```java
builder.setPosition(APosition.fromLocation(new Location(world, x, y, z)));
```

> It can be a moving location

```java
builder.setPosition(APosition.fromLocation(new CallbackWithPreviousValueVariable<>(
        new Location(world, x, y, z), //Start location
        (iterationCount, previousValue) -> previousValue.add(0, 1, 0)//How the location evolves over time
)));
```

> It can be an entity location

```json
builder.setPosition(APosition.fromEntity(player));
```

> It can be a position relative to an entity location

```json
builder.setPosition(APosition.fromEntity(player, new CallbackVariable<>(iterationCount -> 
        player.getLocation().getDirection().normalize().multiply(2) // two blocks in front of the player
)));
```

#### ticksDuration

> Duration of the animation in ticks.
> In minecraft, a second correspond to 20 ticks if the server works without slowdowns. 

```java
builder.setTicksDuration(int ticksDuration)
```

##### Example

> In order for the animation to last 10s.

```java
builder.setTicksDuration(200);
```

#### plugin

> The plugin calling the animation

```java
builder.setPlugin(JavaPlugin plugin)
```

##### Example

> If you have direct access to the plugin class instance.

```java
builder.setPlugin(myPlugin);
```

> Else you should implement the Singleton design pattern.

```java
builder.setPlugin(MyPlugin.getInstance());
```

#### showPeriod

> The time interval between each time the particles of the animation will be shown
> Since most Minecraft particles last more than 1 tick, it is useless to show the particles every tick.
> Showing the particles every tick may also require a gigantic network usage.

```java
builder.setShowPeriod(IVariable<Integer> showPeriod)
```

##### Examples

> The animation particles will be shown every 3 tick

```java
builder.setShowPeriod(3);
```

> This period can also evolve over time.

```java
builder.setShowPeriod(new CallbackVariable<>(iterationCount ->
        iterationCount > 60 ? 2 : 3 // the animations particles will be shown every 3 tick during 3 seconds. Then they will be shown every 2 tick.
));
```

#### callback

> some code that will be called when the animation ends
> This can be used to chain animations.

```java
builder.setCallback(AnimationEndedCallback callback)
```

##### Exemple

> Send a message to everyone when the animation ends.

```java
builder.setCallback(animationEnding ->
        Bukkit.broadcastMessage("The animation " + animationEnding.getClass().getSimpleName() + " ended."
));
```

#### viewers

> Who will see the particles.
> This is important to reduce network usage.

```java
builder.setViewers(AViewers viewers)
```

##### Example

> See it only for players located at less than 50 blocks

```java
builder.setViewers(AViewers.fromNearbyPlayers(50));
```

> See it only for player in the same world

```java
builder.setViewers(AViewers.fromWorldPlayers());
```

> See it only for specified players

```java
Collection<Player> players = new ArrayList<>();
players.add(player1);
players.add(player2);
circleBuilder.setViewers(AViewers.fromCustomPlayers(players));
```

> Only sneeking or low life player will see it

```java
circleBuilder.setViewers(AViewers.fromPredicateMatchingPlayers((player, location) -> 
    player.isSneaking() || player.getHealth() < 2 
));
```

#### rotation

> Rotates the animation over time
> Each rotation will be of an angle rotationAngleAlpha
> Each rotation will be performed around the axis
> 
> This parameter is available on most animations but not all

```java
setRotation(IVariable<Vector> axis, IVariable<Double> rotationAngleAlpha
```

##### Example

> Simply rotating

```java
builder.setRotation(
        new Vector(0, 1, 0),
        Math.PI / 10
);
```

> Accelerating rotation

```java
builder.setRotation(
        new Constant<>(new Vector(0, 1, 0)),
        new DoublePeriodicallyEvolvingVariable(Math.toRadians(0), Math.toRadians(1), 0) //
);
```

> Randomized rotation axis

```java
builder.setRotation(
        new CallbackWithPreviousValueVariable<Vector>(
                new Vector(0, 1, 0),
                (iterationCount, previousValue) -> previousValue.add(new Vector(Math.random() / 4, Math.random() / 4, Math.random() / 4)).normalize()
        ),
        Math.PI / 10
);
```

### Animation specific parameters

#### Line

##### Example

#### Circle

#### Cuboid

#### Sphere

#### Parabola

#### Lighting

#### Spiral

#### Wave

#### Atom

#### Image

#### Obj

#### Text

#### Rose

#### Epi

#### Nodes

## Contribute

* First init git submodules :

```bash
git submodule init
git submodule update
```

* Then reload gradle config.