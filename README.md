# ParticleAnimLib

This library allows developers to include complex minecraft particles animations and trails within their own spigot plugins.

## Introduction

This project is a library, not a plugin. It has to be included in other plugins to work.

## Usage

### 1. Include the dependency in your project

#### a) Using Gradle

```properties
repositories {
  maven {
    url "https://repository.lasers-enigma.eu:443/r"
  }
}

dependencies {
  implementation 'fr.skytale:particle-animation-lib:3.1.0'
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
      <version>3.1.0</version>
  </dependency>
</dependencies>    
```

### 2. Use the library features wherever you need it

#### To create particle animations

```java
/** Create a builder **/
CuboidBuilder cuboidBuilder = new CuboidBuilder();

/** Define the common attributes **/
// Your plugin is required
cuboidBuilder.setJavaPlugin(plugin);

// Define how long (in ticks) the animation will be shown
cuboidBuilder.setTicksDuration(400);

// Define at witch frequency (in ticks) the particles will be shown
cuboidBuilder.setShowFrequency(5);

// Define the particle that will be shown
cuboidBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));

// Define the location of the animation (if it does not move)
//cuboidBuilder.setLocation(myLocation);

// Or define a location relative to an entity
cuboidBuilder.setMovingEntity(player);
cuboidBuilder.setRelativeLocation(new Vector(0, 0, 0));

/** Define the specific attributes, depending on your animation type **/

// Required attributes like the 2 points that defines this cuboid
cuboidBuilder.setFromLocationToFirstCorner(new Vector(-3, -3, -3));
cuboidBuilder.setFromLocationToSecondCorner(new Vector(3, 3, 3));

// The space separating each particle location during the edges drawing
cuboidBuilder.setStep(0.4);

// And potentially optional attributes like a rotation axis and the rotation angle
cuboidBuilder.setAxis(new Vector(0, 1, 0));
cuboidBuilder.setStepAngleAlpha(Math.toRadians(5));

// Show the animation to your players
cuboidBuilder.getAnimation().show();
```

> You can save the result of getAnimation() (cache it within an attribute) in order to be able to show the animation multiple times in a row.
> You can also change the parameters of the animation between each show.

#### To create trails behind players

```java

/** Create a circle animation **/
CircleBuilder circleBuilder = new CircleBuilder();
circleBuilder.setRadius(2.0);
circleBuilder.setNbPoints(20);
circleBuilder.setRelativeLocation(new Vector(0, 0, 0));
circleBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
circleBuilder.setMoveStepVector(new Vector(0,0.2,0));
circleBuilder.setMoveFrequency(1);

circleBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
circleBuilder.setTicksDuration(400);
circleBuilder.setShowFrequency(5);
circleBuilder.setJavaPlugin(plugin);

/** Create the trail **/
TrailBuilder trailBuilder = new TrailBuilder();
trailBuilder.setDuration(Duration.ofSeconds(200));
trailBuilder.setCheckFrequency(2);
trailBuilder.setMinPlayerToAnimationDistance(1);
trailBuilder.setMinDistanceBetweenAnimations(2);
trailBuilder.addAnimation(circleBuilder.getAnimation());

return trailBuilder.getTrail().start();
```

> You can save the TrailTask (cache it within an attribute) in order to be able to add the trail to other players later.
> You can also change the parameters of the Trail and its animation(s) before creating another task from it.

### 3. Go further

#### [> See more advanced examples in the code <](https://gitlab.com/skytale_/skytale-mc/particleanimlib/-/tree/master/src/main/java/fr/skytale/particleanimlib/testing/samples)

## Bundled testing system

In order to try out the library or during development, you may want to activate the testing system.

In order to let users test quickly every animation, a testing system has been bundled within the particle animation library. You simply have to activate it in order to be able to access to some useful commands and features.

> Do not let the testing system activated in production

#### a) (optional) Add a new command to your plugin.yml

```yml
commands:
  panim:
    description: Particle animations
    usage: >
      /panim
      /panim showall
      /panim event
      /panim type <type>
      /panim trail
      /panim trail <type>
    aliases:
      - pa
```

#### b) Activate the testing system

```java
//With the previously defined command
ParticleAnimLibTest.enable(this, Bukkit.getPluginCommand("panim"));

//Or without it
ParticleAnimLibTest.enable(this, null);
```

#### c) Slightly modify your pom/gradle.build

In order to make the Image Sample work, you will have to include the resources located in "/images/" folder inside your final plugin jar.

With gradle, this can be done using the "shadowJar" task and by adding the following to your gradle.build file :

```properties
plugins {
    id "java"
    id "com.github.johnrengelman.shadow" version "5.2.0"
}

shadowJar {
    append 'images/'
}
```

#### Use this testing system in game

* Use the testing system :
  * Right click air to show the currently selected animation (or the default one)
  * Or Use the commands
    * `/panim` : starts the currently selected animation (or the default one)
    * `/panim event` : Toggle the showing of the animation on right click air
    * `/panim type <AnimationType>` : Select the animation to be shown
    * `/panim showall` : Show all the animations.
    * `/panim trail` : Show the selected trail animation (or the default one)
    * `/panim trail <TrailType>` : Select the trail animation to be shown