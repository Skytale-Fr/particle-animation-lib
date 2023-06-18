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
  implementation 'fr.skytale:particle-animation-lib:4.10.1'
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
      <version>4.10.1</version>
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
    * `/panim target <player` : Add player as a target for searching animation
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

#### Lightning

#### Helix

#### Wave

#### Atom

#### Image

#### Obj

#### Text

#### Rose

#### Epi

#### Nodes
=======

## Add collisions to your animations

You are also able to add collisions to your animations.

### Collision types

You can see those checks from two angles:

- **PER_PARTICLE**: Collision checks for every particle (with their associated locations).
  - Example: For a sphere, you can check if a particle of the animation hits an entity.
- **MAIN_POSITION**: Collision checks at the animation level (only once for the animation main location).
  - Example: For a sphere, you can check if an entity is in the sphere.

### Create your first collision builder

A collision builder is a class that will help you to create a CollisionHandler that will be used
by the ParticleAnimLib to perform your collision checks. Here is a simple example to construct your first handler:

````java
SphereBuilder sphereBuilder = // ... create and setup your sphere builder

// A CollisionBuilder needs the target you want to interact with, and the related animation task.
// Here we are plugging our collisions in a sphere animation,
// and we want to interact with entities.        
CollisionBuilder<Entity, SphereTask> collisionBuilder = new CollisionBuilder<>();
collisionBuilder.setJavaPlugin(lineBuilder.getJavaPlugin());
// The following lambda will be called when the animation needs to know
// which targets (here entities) you want to check their potential collisions.
collisionBuilder.setPotentialCollidingTargetsCollector(sphereTask -> {
    // So here, we need to fetch the location of the sphere animation (center)
    Location currentIterationBaseLocation = sphereTask.getCurrentIterationBaseLocation();
    // And get the nearby entities (we use a 10,10,10 bounding box around because the sphere's radius is around 6).
    return currentIterationBaseLocation.getWorld().getNearbyEntities(currentIterationBaseLocation, 10, 10, 10);
});
// The following lambdas will be called after the collector to filter the collected targets.
// As an example, we don't want to collect entities that are players to avoid hurting the animation launcher.
collisionBuilder.addPotentialCollidingTargetsFilter((entity, lineTask) -> entity.getType().equals(EntityType.PLAYER));

// Here comes the hard part. In this simple exemple, we want to check collisions between an
// entity's bounding box and the location of a particle of the animation.
// So we need to use the ParticleCollisionProcessor (type := PER_PARTICLE) to handle this type of behavior.
// Then we are using a default collision preset (already programmed) for entities and their bounding box.
// Finally we provide the action callback called when the provided collision predicate returns true.
// This lambda needs to return an integer that represents a tick duration: will be decrement every ticks
// and since this value is greater than 0, the target can no longer be part of the action callback process.
collisionBuilder.addCollisionProcessor(ParticleCollisionProcessor.useDefault(lineBuilder, EntityCollisionPreset.EXACT_BOUNDING_BOX, (animationTask, target) -> {
    if(!(target instanceof LivingEntity)) return 0; // If the target isn't a living entity, we reject it.
    ((LivingEntity) target).damage(1); // Otherwise we put damage to the living entity.
    return 20; // The entity can only take damages every 20 ticks (every 1 second if the server is at 20 TPS).
}));

// Finally we register our built collision handler to our animation.
sphereBuilder.addCollisionHandler(collisionBuilder.build());
````

## Sub animations collisions

We can take the simple example of a sphere that has a sub animation of lines
(so there would be lines going from the center to the original particles location of the sphere).
Here we want to check if the particles off every sub line animations collides with entities.

````java
SphereBuilder sphereBuilder = // ... create and setup your sphere builder
LineBuilder lienBuilder = // ... create and setup your line builder
sphereBuilder.setPointDefinition(new SubAnimPointDefinition(lineBuilder.getAnimation()));

CollisionBuilder collisionBuilder = // ... create and setup your collision builder

// To fit the above example, you need to add your built collision handler to the sub animation:
lineBuilder.addCollisionHandler(collisionBuilder.build());
````

## More complexes examples

#### Example 1: Check if an entity is inside the sphere
Here we want to check if an entity is inside a sphere shown with the ParticleAnimLib.
This way, we want to keep computation simple and only check if the center of the entity's bounding box
is in the sphere. There is already a collision preset for entities and for sphere animations to match our problem:

````java
SphereBuilder sphereBuilder = new SphereBuilder();
sphereBuilder.setPosition(/* set your position */);
sphereBuilder.setJavaPlugin(/* set your java plugin*/);
sphereBuilder.setRadius(4);
sphereBuilder.setNbCircles(8);
sphereBuilder.setAngleBetweenEachPoint(Math.PI / 4);
sphereBuilder.setMainParticle(new ParticleTemplate(ParticleEffect.REDSTONE, new Color(255, 170, 0))));
sphereBuilder.setSphereType(Sphere.Type.FULL);
sphereBuilder.setTicksDuration(100);
sphereBuilder.setShowPeriod(5);

CollisionBuilder<Entity, SphereTask> collisionBuilder = new CollisionBuilder<>();
collisionBuilder.setJavaPlugin(sphereBuilder.getJavaPlugin());
collisionBuilder.setPotentialCollidingTargetsCollector(lineTask -> {
  Location currentIterationBaseLocation = lineTask.getCurrentIterationBaseLocation();
  return currentIterationBaseLocation.getWorld().getNearbyEntities(currentIterationBaseLocation, 10, 10, 10);
});
collisionBuilder.addPotentialCollidingTargetsFilter((entity, lineTask) -> entity.getType().equals(EntityType.PLAYER));
// EntityCollisionPreset.TARGET_CENTER_INSIDE_SPHERE is the collision preset that we want to use.
// (don't mind to check by userself how this collision test has been made).
collisionBuilder.addCollisionProcessor(SimpleCollisionProcessor.useDefault(sphereBuilder, EntityCollisionPreset.TARGET_CENTER_INSIDE_SPHERE, (animationTask, target) -> {
  if(!(target instanceof LivingEntity)) return -1;
  ((LivingEntity) target).damage(1);
  return 20; // The entity can only take damages every 20 ticks.
}));

sphereBuilder.addCollisionHandler(collisionBuilder.build());
````

#### Example 2: Check if an entity is inside a circle

````java
CircleBuilder circleBuilder = new CircleBuilder();
circleBuilder.setPosition(/* set your position */);
circleBuilder.setJavaPlugin(/* set your java plugin*/);
circleBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
circleBuilder.setNbPoints(20, true);
circleBuilder.setRadius(4);
circleBuilder.setMainParticle(new ParticleTemplate(ParticleEffect.REDSTONE, new Color(255, 170, 0))));
circleBuilder.setTicksDuration(100);
circleBuilder.setShowPeriod(new Constant<>(1));

CollisionBuilder<Entity, CircleTask> collisionBuilder = new CollisionBuilder<>();
collisionBuilder.setJavaPlugin(circleBuilder.getJavaPlugin());
collisionBuilder.setPotentialCollidingTargetsCollector(lineTask -> {
  Location currentIterationBaseLocation = lineTask.getCurrentIterationBaseLocation();
  return currentIterationBaseLocation.getWorld().getNearbyEntities(currentIterationBaseLocation, 10, 10, 10);
});
collisionBuilder.addPotentialCollidingTargetsFilter((entity, lineTask) -> entity.getType().equals(EntityType.PLAYER));
// So here we are using the SimpleCollisionProcessor (type := MAIN_POSITION).
// This way, the following processor will be performed only once per animation show and get provided with
// the animation's main location (here the center of the circle).
// And we are also using the EntityCollisionPreset.TARGET_CENTER_INSIDE_CIRCLE collision preset that match perfectly or problem.
// (don't mind to check by userself how this collision test has been made).
collisionBuilder.addCollisionProcessor(SimpleCollisionProcessor.useDefault(circleBuilder, EntityCollisionPreset.TARGET_CENTER_INSIDE_CIRCLE, (animationTask, target) -> {
  if(!(target instanceof LivingEntity)) return -1;
  ((LivingEntity) target).damage(1);
  return 20; // The entity can only take damages every 20 ticks.
}));

circleBuilder.addCollisionHandler(collisionBuilder.build());
````

## Contribute

* First init git submodules :

```bash
git submodule init
git submodule update
```

* Then reload gradle config.