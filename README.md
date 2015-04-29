Baguette
=======

[![Build Status](https://travis-ci.org/florent37/Baguette.svg)](https://travis-ci.org/florent37/Baguette)

Baguette is an Android Toast implementation adapted for Android Wear

![alt sample](https://raw.githubusercontent.com/florent37/Baguette/master/wear/src/main/res/drawable/baguette_sample_2.png)

Download
--------

In your root build.gradle add
```groovy
repositories {
    maven {
        url  "http://dl.bintray.com/florent37/maven"
    }
}
```

In your wear module [![Download](https://api.bintray.com/packages/florent37/maven/Baguette/images/download.svg)](https://bintray.com/florent37/maven/WearMenu/_latestVersion)
```groovy
compile 'com.github.florent37:baguette:1.0.0@aar'
```

Usage
--------

```java
Baguette.makeText(getContext(), "text to display", Baguette.LENGTH_SHORT).show();
Baguette.makeText(getContext(), R.string.baguette_sample, Baguette.LENGTH_SHORT).show();
```

![alt sample](https://raw.githubusercontent.com/florent37/Baguette/master/wear/src/main/res/drawable/baguette_sample_2.png)

```java
Baguette.makeText(getContext(), R.string.baguette_sample, Baguette.LENGTH_SHORT).enableUndo(new Baguette.BaguetteListener() {
                    @Override
                    public void onActionClicked() {
                        Log.d(TAG, "undo");
                    }
                }).show();
```

![alt sample](https://raw.githubusercontent.com/florent37/Baguette/master/wear/src/main/res/drawable/baguette_sample.png)


Community
--------

Looking for contributors, feel free to fork !

Wear
--------

If you want to learn wear development : [http://tutos-android-france.com/developper-une-application-pour-les-montres-android-wear/][tuto_wear].

Credits
-------

Author: Florent Champigny

<a href="https://plus.google.com/+florentchampigny">
  <img alt="Follow me on Google+"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/gplus.png" />
</a>
<a href="https://twitter.com/florent_champ">
  <img alt="Follow me on Twitter"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/twitter.png" />
</a>
<a href="https://www.linkedin.com/profile/view?id=297860624">
  <img alt="Follow me on LinkedIn"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/linkedin.png" />
</a>


Pictures by Logan Bourgouin

<a href="https://plus.google.com/+LoganBOURGOIN">
  <img alt="Follow me on Google+"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/gplus.png" />
</a>

License
--------

    Copyright 2015 florent37, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


[snap]: https://oss.sonatype.org/content/repositories/snapshots/
[tuto_wear]: http://tutos-android-france.com/developper-une-application-pour-les-montres-android-wear/