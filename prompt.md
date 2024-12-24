Project Path: neoforge_circlemod

Source Tree:

```
neoforge_circlemod
├── build.gradle
├── genPrompt.bat
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradle.properties
├── gradlew
├── gradlew.bat
├── README.md
├── settings.gradle
├── src
│   ├── generated
│   │   └── resources
│   │       └── assets
│   │           └── circle
│   │               └── lang
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── circle
│   │   │           └── circlemod
│   │   │               ├── core
│   │   │               │   ├── CircleMod.java
│   │   │               │   ├── event
│   │   │               │   │   ├── DataGen.java
│   │   │               │   │   └── RegisterEvent.java
│   │   │               │   ├── payload
│   │   │               │   │   └── SyncMovementPayload.java
│   │   │               │   └── resource
│   │   │               │       ├── CircleResource.java
│   │   │               │       ├── entity
│   │   │               │       │   └── vanilla_extends
│   │   │               │       │       └── item_entity
│   │   │               │       │           └── CircleItemEntity.java
│   │   │               │       ├── item
│   │   │               │       │   ├── axolotl_sword
│   │   │               │       │   │   └── AxolotlSword.java
│   │   │               │       │   ├── goldstaff
│   │   │               │       │   │   └── GoldStaff.java
│   │   │               │       │   └── pickaxe_ultra
│   │   │               │       │       └── PickaxeUltra.java
│   │   │               │       └── ResourceLocation.java
│   │   │               ├── datagen
│   │   │               │   └── provider
│   │   │               │       └── LangProvider.java
│   │   │               └── tools
│   │   │                   ├── EnchantmentTool.java
│   │   │                   ├── StyleTool.java
│   │   │                   └── TranslateTool.java
│   │   ├── resources
│   │   │   ├── assets
│   │   │   │   └── circle
│   │   │   │       ├── item
│   │   │   │       ├── models
│   │   │   │       │   └── item
│   │   │   │       │       ├── gold_staff.json
│   │   │   │       │       └── pickaxe_ultra.json
│   │   │   │       ├── recipes
│   │   │   │       │   └── pickaxe_ultra.json
│   │   │   │       └── textures
│   │   │   │           └── item
│   │   │   │               └── gold_staff.png
│   │   │   └── META-INF
│   │   │       └── accesstransformer.cfg
│   │   └── templates
│   │       └── META-INF
│   │           └── neoforge.mods.toml
│   └── test
│       └── java
│           └── com
│               └── circle
│                   └── circlemod
│                       └── core
│                           └── resource
│                               └── entity
│                                   └── vanilla_extends
│                                       └── item_entity
└── TEMPLATE_LICENSE.txt

```

`\\?\D:\code\projects\neoforge_circlemod\build.gradle`:

```gradle
plugins {
    id 'java-library'
    id 'maven-publish'
    id 'net.neoforged.moddev' version '1.0.21'
}

tasks.named('wrapper', Wrapper).configure {
    // Define wrapper values here so as to not have to always do so when updating gradlew.properties.
    // Switching this to Wrapper.DistributionType.ALL will download the full gradle sources that comes with
    // documentation attached on cursor hover of gradle classes and methods. However, this comes with increased
    // file size for Gradle. If you do switch this to ALL, run the Gradle wrapper task twice afterwards.
    // (Verify by checking gradle/wrapper/gradle-wrapper.properties to see if distributionUrl now points to `-all`)
    distributionType = Wrapper.DistributionType.BIN
}

version = mod_version
group = mod_group_id

repositories {
    mavenLocal()

    maven {
        name = 'GeckoLib'
        url 'https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/'
        content {
            includeGroup("software.bernie.geckolib")
        }
    }
}

base {
    archivesName = mod_id
}

// Mojang ships Java 21 to end users starting in 1.20.5, so mods should target Java 21.
java.toolchain.languageVersion = JavaLanguageVersion.of(21)

neoForge {
    // Specify the version of NeoForge to use.
    version = project.neo_version

    parchment {
        mappingsVersion = project.parchment_mappings_version
        minecraftVersion = project.parchment_minecraft_version
    }

    // This line is optional. Access Transformers are automatically detected
    // accessTransformers = project.files('src/main/resources/META-INF/accesstransformer.cfg')

    // Default run configurations.
    // These can be tweaked, removed, or duplicated as needed.
    runs {
        client {
            dependencies {
                data()
            }
            client()
            // Comma-separated list of namespaces to load gametests from. Empty = all namespaces.
            systemProperty 'neoforge.enabledGameTestNamespaces', project.mod_id
        }

        server {
            server()
            programArgument '--nogui'
            systemProperty 'neoforge.enabledGameTestNamespaces', project.mod_id
        }

        // This run config launches GameTestServer and runs all registered gametests, then exits.
        // By default, the server will crash when no gametests are provided.
        // The gametest system is also enabled by default for other run configs under the /test command.
        gameTestServer {
            type = "gameTestServer"
            systemProperty 'neoforge.enabledGameTestNamespaces', project.mod_id
        }

        data {
            data()

            // example of overriding the workingDirectory set in configureEach above, uncomment if you want to use it
            // gameDirectory = project.file('run-data')

            // Specify the modid for data generation, where to output the resulting resource, and where to look for existing resources.
            programArguments.addAll '--mod', project.mod_id, '--all', '--output', file('src/generated/resources/').getAbsolutePath(), '--existing', file('src/main/resources/').getAbsolutePath()
        }

        // applies to all the run configs above
        configureEach {
            // Recommended logging data for a userdev environment
            // The markers can be added/remove as needed separated by commas.
            // "SCAN": For mods scan.
            // "REGISTRIES": For firing of registry events.
            // "REGISTRYDUMP": For getting the contents of all registries.
            systemProperty 'forge.logging.markers', 'REGISTRIES'

            // Recommended logging level for the console
            // You can set various levels here.
            // Please read: https://stackoverflow.com/questions/2031163/when-to-use-the-different-log-levels
            logLevel = org.slf4j.event.Level.DEBUG
        }

    }

    mods {
        // define mod <-> source bindings
        // these are used to tell the game which sources are for which mod
        // mostly optional in a single mod project
        // but multi mod projects should define one per mod
        "${mod_id}" {
            sourceSet(sourceSets.main)
        }
    }
}

// Include resources generated by data generators.
sourceSets.main.resources { srcDir 'src/generated/resources' }

// Sets up a dependency configuration called 'localRuntime'.
// This configuration should be used instead of 'runtimeOnly' to declare
// a dependency that will be present for runtime testing but that is
// "optional", meaning it will not be pulled by dependents of this mod.
configurations {
    runtimeClasspath.extendsFrom localRuntime
}

dependencies {
    // Example optional mod dependency with JEI
    // The JEI API is declared for compile time use, while the full JEI artifact is used at runtime
    // compileOnly "mezz.jei:jei-${mc_version}-common-api:${jei_version}"
    // compileOnly "mezz.jei:jei-${mc_version}-neoforge-api:${jei_version}"
    // We add the full version to localRuntime, not runtimeOnly, so that we do not publish a dependency on it
    // localRuntime "mezz.jei:jei-${mc_version}-neoforge:${jei_version}"

    // Example mod dependency using a mod jar from ./libs with a flat dir repository
    // This maps to ./libs/coolmod-${mc_version}-${coolmod_version}.jar
    // The group id is ignored when searching -- in this case, it is "blank"
    // implementation "blank:coolmod-${mc_version}:${coolmod_version}"

    // Example mod dependency using a file as dependency
    // implementation files("libs/coolmod-${mc_version}-${coolmod_version}.jar")

    // Example project dependency using a sister or child project:
    // implementation project(":myproject")

    // For more info:
    // http://www.gradle.org/docs/current/userguide/artifact_dependencies_tutorial.html
    // http://www.gradle.org/docs/current/userguide/dependency_management.html

    implementation "software.bernie.geckolib:geckolib-neoforge-${minecraft_version}:${geckolib_version}"
}

// This block of code expands all declared replace properties in the specified resource targets.
// A missing property will result in an error. Properties are expanded using ${} Groovy notation.
var generateModMetadata = tasks.register("generateModMetadata", ProcessResources) {
    var replaceProperties = [
            minecraft_version      : minecraft_version,
            minecraft_version_range: minecraft_version_range,
            neo_version            : neo_version,
            neo_version_range      : neo_version_range,
            loader_version_range   : loader_version_range,
            mod_id                 : mod_id,
            mod_name               : mod_name,
            mod_license            : mod_license,
            mod_version            : mod_version,
            mod_authors            : mod_authors,
            mod_description        : mod_description
    ]
    inputs.properties replaceProperties
    expand replaceProperties
    from "src/main/templates"
    into "build/generated/sources/modMetadata"
}
// Include the output of "generateModMetadata" as an input directory for the build
// this works with both building through Gradle and the IDE.
sourceSets.main.resources.srcDir generateModMetadata
// To avoid having to run "generateModMetadata" manually, make it run on every project reload
neoForge.ideSyncTask generateModMetadata

// Example configuration to allow publishing using the maven-publish plugin
publishing {
    publications {
        register('mavenJava', MavenPublication) {
            from components.java
        }
    }
    repositories {
        maven {
            url "file://${project.projectDir}/repo"
        }
    }
}

tasks.withType(JavaCompile).configureEach {
    options.encoding = 'UTF-8' // Use the UTF-8 charset for Java compilation
}

// IDEA no longer automatically downloads sources/javadoc jars for dependencies, so we need to explicitly enable the behavior.
idea {
    module {
        downloadSources = true
        downloadJavadoc = true
    }
}

```

`\\?\D:\code\projects\neoforge_circlemod\genPrompt.bat`:

```bat
code2prompt ./ -o prompt.md
```

`\\?\D:\code\projects\neoforge_circlemod\gradle\wrapper\gradle-wrapper.properties`:

```properties
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.9-bin.zip
networkTimeout=10000
validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists

```

`\\?\D:\code\projects\neoforge_circlemod\gradle.properties`:

```properties
# Sets default memory used for gradle commands. Can be overridden by user or command line properties.
org.gradle.jvmargs=-Xmx1G
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.configuration-cache=true

#read more on this at https://github.com/neoforged/ModDevGradle?tab=readme-ov-file#better-minecraft-parameter-names--javadoc-parchment
# you can also find the latest versions at: https://parchmentmc.org/docs/getting-started
parchment_minecraft_version=1.21.1
parchment_mappings_version=2024.11.13
# Environment Properties
# You can find the latest versions here: https://projects.neoforged.net/neoforged/neoforge
# The Minecraft version must agree with the Neo version to get a valid artifact
minecraft_version=1.21.1
# The Minecraft version range can use any release version of Minecraft as bounds.
# Snapshots, pre-releases, and release candidates are not guaranteed to sort properly
# as they do not follow standard versioning conventions.
minecraft_version_range=[1.21.1, 1.22)
# The Neo version must agree with the Minecraft version to get a valid artifact
neo_version=21.1.77
# The Neo version range can use any version of Neo as bounds
neo_version_range=[21.1.0,)
# The loader version range can only use the major version of FML as bounds
loader_version_range=[4,)

geckolib_version=4.7
## Mod Properties

# The unique mod identifier for the mod. Must be lowercase in English locale. Must fit the regex [a-z][a-z0-9_]{1,63}
# Must match the String constant located in the main mod class annotated with @Mod.
mod_id=circle
# The human-readable display name for the mod.
mod_name=circle mod
# The license of the mod. Review your options at https://choosealicense.com/. All Rights Reserved is the default.
mod_license=All Rights Reserved
# The mod version. See https://semver.org/
mod_version=1.0.0
# The group ID for the mod. It is only important when publishing as an artifact to a Maven repository.
# This should match the base package used for the mod sources.
# See https://maven.apache.org/guides/mini/guide-naming-conventions.html
mod_group_id=com.circle.circlemod
# The authors of the mod. This is a simple text string that is used for display purposes in the mod list.
mod_authors=Yuanxin
# The description of the mod. This is a simple multiline text string that is used for display purposes in the mod list.
mod_description=Circle mod


```

`\\?\D:\code\projects\neoforge_circlemod\gradlew`:

```
#!/bin/sh

#
# Copyright © 2015-2021 the original authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# SPDX-License-Identifier: Apache-2.0
#

##############################################################################
#
#   Gradle start up script for POSIX generated by Gradle.
#
#   Important for running:
#
#   (1) You need a POSIX-compliant shell to run this script. If your /bin/sh is
#       noncompliant, but you have some other compliant shell such as ksh or
#       bash, then to run this script, type that shell name before the whole
#       command line, like:
#
#           ksh Gradle
#
#       Busybox and similar reduced shells will NOT work, because this script
#       requires all of these POSIX shell features:
#         * functions;
#         * expansions «$var», «${var}», «${var:-default}», «${var+SET}»,
#           «${var#prefix}», «${var%suffix}», and «$( cmd )»;
#         * compound commands having a testable exit status, especially «case»;
#         * various built-in commands including «command», «set», and «ulimit».
#
#   Important for patching:
#
#   (2) This script targets any POSIX shell, so it avoids extensions provided
#       by Bash, Ksh, etc; in particular arrays are avoided.
#
#       The "traditional" practice of packing multiple parameters into a
#       space-separated string is a well documented source of bugs and security
#       problems, so this is (mostly) avoided, by progressively accumulating
#       options in "$@", and eventually passing that to Java.
#
#       Where the inherited environment variables (DEFAULT_JVM_OPTS, JAVA_OPTS,
#       and GRADLE_OPTS) rely on word-splitting, this is performed explicitly;
#       see the in-line comments for details.
#
#       There are tweaks for specific operating systems such as AIX, CygWin,
#       Darwin, MinGW, and NonStop.
#
#   (3) This script is generated from the Groovy template
#       https://github.com/gradle/gradle/blob/HEAD/platforms/jvm/plugins-application/src/main/resources/org/gradle/api/internal/plugins/unixStartScript.txt
#       within the Gradle project.
#
#       You can find Gradle at https://github.com/gradle/gradle/.
#
##############################################################################

# Attempt to set APP_HOME

# Resolve links: $0 may be a link
app_path=$0

# Need this for daisy-chained symlinks.
while
    APP_HOME=${app_path%"${app_path##*/}"}  # leaves a trailing /; empty if no leading path
    [ -h "$app_path" ]
do
    ls=$( ls -ld "$app_path" )
    link=${ls#*' -> '}
    case $link in             #(
      /*)   app_path=$link ;; #(
      *)    app_path=$APP_HOME$link ;;
    esac
done

# This is normally unused
# shellcheck disable=SC2034
APP_BASE_NAME=${0##*/}
# Discard cd standard output in case $CDPATH is set (https://github.com/gradle/gradle/issues/25036)
APP_HOME=$( cd -P "${APP_HOME:-./}" > /dev/null && printf '%s
' "$PWD" ) || exit

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD=maximum

warn () {
    echo "$*"
} >&2

die () {
    echo
    echo "$*"
    echo
    exit 1
} >&2

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
nonstop=false
case "$( uname )" in                #(
  CYGWIN* )         cygwin=true  ;; #(
  Darwin* )         darwin=true  ;; #(
  MSYS* | MINGW* )  msys=true    ;; #(
  NONSTOP* )        nonstop=true ;;
esac

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar


# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD=$JAVA_HOME/jre/sh/java
    else
        JAVACMD=$JAVA_HOME/bin/java
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD=java
    if ! command -v java >/dev/null 2>&1
    then
        die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
fi

# Increase the maximum file descriptors if we can.
if ! "$cygwin" && ! "$darwin" && ! "$nonstop" ; then
    case $MAX_FD in #(
      max*)
        # In POSIX sh, ulimit -H is undefined. That's why the result is checked to see if it worked.
        # shellcheck disable=SC2039,SC3045
        MAX_FD=$( ulimit -H -n ) ||
            warn "Could not query maximum file descriptor limit"
    esac
    case $MAX_FD in  #(
      '' | soft) :;; #(
      *)
        # In POSIX sh, ulimit -n is undefined. That's why the result is checked to see if it worked.
        # shellcheck disable=SC2039,SC3045
        ulimit -n "$MAX_FD" ||
            warn "Could not set maximum file descriptor limit to $MAX_FD"
    esac
fi

# Collect all arguments for the java command, stacking in reverse order:
#   * args from the command line
#   * the main class name
#   * -classpath
#   * -D...appname settings
#   * --module-path (only if needed)
#   * DEFAULT_JVM_OPTS, JAVA_OPTS, and GRADLE_OPTS environment variables.

# For Cygwin or MSYS, switch paths to Windows format before running java
if "$cygwin" || "$msys" ; then
    APP_HOME=$( cygpath --path --mixed "$APP_HOME" )
    CLASSPATH=$( cygpath --path --mixed "$CLASSPATH" )

    JAVACMD=$( cygpath --unix "$JAVACMD" )

    # Now convert the arguments - kludge to limit ourselves to /bin/sh
    for arg do
        if
            case $arg in                                #(
              -*)   false ;;                            # don't mess with options #(
              /?*)  t=${arg#/} t=/${t%%/*}              # looks like a POSIX filepath
                    [ -e "$t" ] ;;                      #(
              *)    false ;;
            esac
        then
            arg=$( cygpath --path --ignore --mixed "$arg" )
        fi
        # Roll the args list around exactly as many times as the number of
        # args, so each arg winds up back in the position where it started, but
        # possibly modified.
        #
        # NB: a `for` loop captures its iteration list before it begins, so
        # changing the positional parameters here affects neither the number of
        # iterations, nor the values presented in `arg`.
        shift                   # remove old arg
        set -- "$@" "$arg"      # push replacement arg
    done
fi


# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Collect all arguments for the java command:
#   * DEFAULT_JVM_OPTS, JAVA_OPTS, JAVA_OPTS, and optsEnvironmentVar are not allowed to contain shell fragments,
#     and any embedded shellness will be escaped.
#   * For example: A user cannot expect ${Hostname} to be expanded, as it is an environment variable and will be
#     treated as '${Hostname}' itself on the command line.

set -- \
        "-Dorg.gradle.appname=$APP_BASE_NAME" \
        -classpath "$CLASSPATH" \
        org.gradle.wrapper.GradleWrapperMain \
        "$@"

# Stop when "xargs" is not available.
if ! command -v xargs >/dev/null 2>&1
then
    die "xargs is not available"
fi

# Use "xargs" to parse quoted args.
#
# With -n1 it outputs one arg per line, with the quotes and backslashes removed.
#
# In Bash we could simply go:
#
#   readarray ARGS < <( xargs -n1 <<<"$var" ) &&
#   set -- "${ARGS[@]}" "$@"
#
# but POSIX shell has neither arrays nor command substitution, so instead we
# post-process each arg (as a line of input to sed) to backslash-escape any
# character that might be a shell metacharacter, then use eval to reverse
# that process (while maintaining the separation between arguments), and wrap
# the whole thing up as a single "set" statement.
#
# This will of course break if any of these variables contains a newline or
# an unmatched quote.
#

eval "set -- $(
        printf '%s\n' "$DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS" |
        xargs -n1 |
        sed ' s~[^-[:alnum:]+,./:=@_]~\\&~g; ' |
        tr '\n' ' '
    )" '"$@"'

exec "$JAVACMD" "$@"

```

`\\?\D:\code\projects\neoforge_circlemod\gradlew.bat`:

```bat
@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem
@rem SPDX-License-Identifier: Apache-2.0
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  Gradle startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo. 1>&2
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH. 1>&2
echo. 1>&2
echo Please set the JAVA_HOME variable in your environment to match the 1>&2
echo location of your Java installation. 1>&2

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo. 1>&2
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME% 1>&2
echo. 1>&2
echo Please set the JAVA_HOME variable in your environment to match the 1>&2
echo location of your Java installation. 1>&2

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar


@rem Execute Gradle
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable GRADLE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%GRADLE_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega

```

`\\?\D:\code\projects\neoforge_circlemod\README.md`:

```md
## CircleMod开发记录

### 注册入口
找到`com.circle.circlemod.resource.CircleResource`，在static块中定义注册内容。

例如注册一个Item物品
```Java
public class CircleResource {
    static {
        CircleUniRegister.registerItem(ResourceLocation.GOLD_STAFF, GoldStaff::new);
    }
}
```
这是neoforge的一个Deferred注册的封装，第一个参数是物品id，第二个参数是一个`Supplier`

GoldStaff就是该物品的实现类，如果没有任何特别的需求，第二个参数可以替换成`() -> new Item.Properties()`。
```

`\\?\D:\code\projects\neoforge_circlemod\settings.gradle`:

```gradle
pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven { url = 'https://maven.neoforged.net/releases' }
    }
}

plugins {
    id 'org.gradle.toolchains.foojay-resolver-convention' version '0.8.0'
}

```

`\\?\D:\code\projects\neoforge_circlemod\src\main\java\com\circle\circlemod\core\CircleMod.java`:

```java
package com.circle.circlemod.core;

import com.circle.circlemod.core.builds.register.CircleUniRegister;
import com.circle.circlemod.core.resource.ResourceLocation;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

/**
 * Circle Mod
 *
 * @author yuanxin
 * @date 2024/12/12
 */
@Mod(CircleMod.MODID)
public class CircleMod {
    public static final String MODID = "circle";
    public static final Logger LOGGER = LogUtils.getLogger();
    public CircleUniRegister circleUniRegister = null;

    /**
     * Circle Mod
     */
    public CircleMod(IEventBus bus, ModContainer modContainer) {
        bus.addListener(this::commonSetup);

        circleUniRegister = new CircleUniRegister(bus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        CircleUniRegister.ITEMS
                .getEntries()
                .forEach(item -> {
                    LOGGER.info("注册物品：{}", ResourceLocation.findEnum(item.getKey()).name);
                });
    }
}

```

`\\?\D:\code\projects\neoforge_circlemod\src\main\java\com\circle\circlemod\core\event\DataGen.java`:

```java
package com.circle.circlemod.core.event;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.datagen.provider.LangProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = CircleMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(
                event.includeClient(),
                new LangProvider(output)
        );
    }
}

```

`\\?\D:\code\projects\neoforge_circlemod\src\main\java\com\circle\circlemod\core\event\RegisterEvent.java`:

```java
package com.circle.circlemod.core.event;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.payload.SyncMovementPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = CircleMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RegisterEvent {


    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(CircleMod.MODID);
        registrar.playBidirectional(SyncMovementPayload.TYPE, SyncMovementPayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(SyncMovementPayload::handle, null));
    }

}

```

`\\?\D:\code\projects\neoforge_circlemod\src\main\java\com\circle\circlemod\core\payload\SyncMovementPayload.java`:

```java
package com.circle.circlemod.core.payload;

import com.circle.circlemod.core.CircleMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.joml.Vector3f;

public record SyncMovementPayload(int entityId, Vector3f center, float radius,
                                  double angle) implements CustomPacketPayload {


    public static final CustomPacketPayload.Type<SyncMovementPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CircleMod.MODID, "sync_movement"));

    public static final StreamCodec<ByteBuf, SyncMovementPayload> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, SyncMovementPayload::entityId, ByteBufCodecs.VECTOR3F, SyncMovementPayload::center, ByteBufCodecs.FLOAT, SyncMovementPayload::radius, ByteBufCodecs.DOUBLE, SyncMovementPayload::angle, SyncMovementPayload::new);


    public static void recieve(SyncMovementPayload data) {
        double angle = data.angle;
        Vector3f center = data.center;
        float radius = data.radius;

        Minecraft mc = Minecraft.getInstance();

        Entity entity = mc.level.getEntity(data.entityId);
        if (entity != null) {
            Vec3 position = entity.getPosition(1);
            float radians = (float) Math.toRadians(angle);
            float sin = Mth.sin(radians);
            float cos = Mth.cos(radians);
            Vector3f normalize = new Vector3f(sin, 0, cos).normalize(radius);

            ParticleUtils.spawnParticles(mc.level, new BlockPos((int) position.x, (int) position.y, (int) position.z), 5, 1, 1, true, ParticleTypes.WAX_ON);
            Vector3f target = center.add(normalize);
            entity.setPos(new Vec3(target));
        }
    }

    public static void handle(final SyncMovementPayload data, final IPayloadContext context) {
        // 处理网络包的方法，它将接收到的数据在服务器端的逻辑线程上w处理
        context.enqueueWork(() -> {
            recieve(data);
        });
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

```

`\\?\D:\code\projects\neoforge_circlemod\src\main\java\com\circle\circlemod\core\resource\CircleResource.java`:

```java
package com.circle.circlemod.core.resource;

import com.circle.circlemod.core.builds.register.CircleUniRegister;
import com.circle.circlemod.core.resource.item.axolotl_sword.AxolotlSword;
import com.circle.circlemod.core.resource.item.goldstaff.GoldStaff;
import com.circle.circlemod.core.resource.item.pickaxe_ultra.PickaxeUltra;

/**
 * Circle 资源
 * 从这里开始注册内容。
 *
 * @author yuanxin
 * @date 2024/12/12
 */
public class CircleResource {
    static {
        CircleUniRegister.registerItem(ResourceLocation.GOLD_STAFF, GoldStaff::new);
        CircleUniRegister.registerItem(ResourceLocation.PICKAXE_ULTRA, () -> new PickaxeUltra());
        CircleUniRegister.registerItem(ResourceLocation.AXOLOTL_SWORD, AxolotlSword::new);

    }
}

```

`\\?\D:\code\projects\neoforge_circlemod\src\main\java\com\circle\circlemod\core\resource\entity\vanilla_extends\item_entity\CircleItemEntity.java`:

```java
package com.circle.circlemod.core.resource.entity.vanilla_extends.item_entity;

import com.circle.circlemod.core.payload.SyncMovementPayload;
import com.circle.circlemod.tools.EnchantmentTool;
import com.mojang.logging.LogUtils;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 拓展原版ItemEntity（丢出去的物品实体）
 *
 * @author yuanxin
 * @date 2024/12/14
 */
public class CircleItemEntity extends ItemEntity {

    private static final Logger LOGGER = LogUtils.getLogger();
    double angle = 0;
    float radius = 3;
    Vector3f center = null;
    Player owner;
    ItemStack useItemStack;
    ArrayList<BlockState> tempBlockStateList = new ArrayList<>();

    /**
     * 丢出去的圈数
     */
    int useCount = 0;

    public CircleItemEntity(Level level, double x, double y, double z, ItemStack stack, @Nullable Player owner) {
        super(level, x, y, z, stack);
        this.setDefaultPickUpDelay();
        this.setNoGravity(true);
        this.setDeltaMovement(new Vec3(0, 0, 0));
        this.center = owner.getPosition(1)
                           .toVector3f();
        this.owner = owner;
        this.useItemStack = stack;
    }


    /**
     * 碰到方块时的逻辑
     * do destory（摧毁）
     */
    public void doDestory(Level level, Vec3 pos, BlockPos blockPos) {
        boolean isDrop = true;

        // 忠诚
        boolean loyalty = EnchantmentTool.hasThisEnchantment(useItemStack, Enchantments.LOYALTY) > 0;
        // 多重射击
        int multiShotLevel = EnchantmentTool.hasThisEnchantment(useItemStack, Enchantments.MULTISHOT);

        // 冲击附魔
        int punchLevel = EnchantmentTool.hasThisEnchantment(useItemStack, Enchantments.PUNCH);

        ArrayList<BlockPos> blocks = new ArrayList<>();
        if (punchLevel != 0) {
            for (int i = 0; i < punchLevel; i++) {
                for (int j = 0; j < punchLevel; j++) {
                    for (int z = 0; z <= multiShotLevel; z++) {
                        blocks.add(blockPos.offset(i, z, j));
                        blocks.add(blockPos.offset(-i, -z, -j));
                    }
                }
            }
        }

        blocks.add(blockPos);
        List<BlockPos> collect = blocks.stream()
                                       .distinct()
                                       .collect(Collectors.toList());
        blocks.clear();
        blocks.addAll(collect);

        // 如果没有忠诚则直接破坏，否则暂存起来，在收起物品的时候才获取物品
        if (loyalty) {
            isDrop = false;
        }

        for (BlockPos bPos : blocks) {
            BlockState blockState = level.getBlockState(bPos);
            tempBlockStateList.add(blockState);
            level.destroyBlock(bPos, isDrop);
        }
    }

    /**
     * 在绕圈完后，获取时触发
     */
    public void afterPickup() {
        tempBlockStateList.forEach(blockState -> {
            ItemEntity itemEntity = new ItemEntity(level(), owner.getX(), owner.getY(), owner.getZ(), blockState.getBlock()
                                                                                                                .asItem()
                                                                                                                .getDefaultInstance());
            level().addFreshEntity(itemEntity);
        });
        tempBlockStateList.clear();
    }

    @Override
    public void tick() {
        super.tick();

        Vec3 position = this.getPosition(1);
        Vec3 ownerPosition = owner.getPosition(1);


        // 旋转超过一圈
        if (useCount > 1) {  // 超过一圈 移动到使用者
            this.moveTo(ownerPosition);
        } else {

            // region 碰到方块，则破坏
            Level level = this.level();
            BlockPos blockPos = new BlockPos((int) position.x, (int) position.y, (int) position.z);
            if (!level.getBlockState(blockPos)
                      .is(Blocks.AIR)) {

                doDestory(level, position, blockPos);
            }
            // endregion

            // region 绕圈旋转
            float radians = (float) Math.toRadians(angle);
            float sin = Mth.sin(radians);
            float cos = Mth.cos(radians);
            Vector3f normalize = new Vector3f(sin, 0, cos).normalize(radians);
            Vector3f target = new Vector3f(center.x + normalize.x, center.y + normalize.y, center.z + normalize.z);
            this.moveTo(new Vec3(target));
            angle = angle % 360;
            angle = (angle + 18);
            if (angle >= 360) {
                useCount++;
            }
            // endregion
        }
        // region 发包给客户端，更新位置数据
        PacketDistributor.sendToAllPlayers(new SyncMovementPayload(this.getId(), center, radius, angle));
    }

    @Override
    public SynchedEntityData getEntityData() {
        return super.getEntityData();
    }

    @Override
    public float getSpin(float partialTicks) {
        return 0;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void push(Vec3 vector) {

    }

    @Override
    public boolean isPickable() {
        return false;
    }


    @Override
    public boolean isColliding(BlockPos pos, BlockState state) {
        return false;
    }


    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public void playerTouch(Player entity) {
        if (useCount > 1) {
            super.playerTouch(entity);
            afterPickup();
        }
    }

}


```

`\\?\D:\code\projects\neoforge_circlemod\src\main\java\com\circle\circlemod\core\resource\item\axolotl_sword\AxolotlSword.java`:

```java
package com.circle.circlemod.core.resource.item.axolotl_sword;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;

/**
 * 美西螈剑
 *
 * @author yuanxin
 * @date 2024/12/23
 */
public class AxolotlSword extends SwordItem {

    /**
     * 修复延迟
     */
    private static final int REPAIR_DELAY = 60; // 3秒（60个tick）
    /**
     * 修复计时器
     */
    private int repairTimer = 0;

    public AxolotlSword(Tier p_tier, Properties p_properties, Tool toolComponentData) {
        super(p_tier, p_properties, toolComponentData);
    }

    public AxolotlSword(Tier tier, Properties properties) {
        super(tier, properties);
    }

    public AxolotlSword() {
        super(Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4F)));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        repairTimer = 0;
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (!level.isClientSide) {
            repairTimer++;
            if (repairTimer > REPAIR_DELAY) {
                if (stack.getMaxDamage() > stack.getDamageValue()) {
                    stack.setDamageValue(stack.getDamageValue() - 1);
                    repairTimer = 0;
                }
            }
        }
    }
}

```

`\\?\D:\code\projects\neoforge_circlemod\src\main\java\com\circle\circlemod\core\resource\item\goldstaff\GoldStaff.java`:

```java
package com.circle.circlemod.core.resource.item.goldstaff;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GoldStaff extends Item {
    public GoldStaff() {
        super(new Item.Properties());
    }

    public GoldStaff(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        interactionTarget.hurt(interactionTarget
                .damageSources()
                .magic(), 10);

        return super.interactLivingEntity(stack, player, interactionTarget, usedHand);
    }
}

```

`\\?\D:\code\projects\neoforge_circlemod\src\main\java\com\circle\circlemod\core\resource\item\pickaxe_ultra\PickaxeUltra.java`:

```java
package com.circle.circlemod.core.resource.item.pickaxe_ultra;

import com.circle.circlemod.core.resource.ResourceLocation;
import com.circle.circlemod.core.resource.entity.vanilla_extends.item_entity.CircleItemEntity;
import com.circle.circlemod.tools.StyleTool;
import com.circle.circlemod.tools.TranslateTool;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 升级版—铁镐
 *
 * @author yuanxin
 * @date 2024/12/14
 */
public class PickaxeUltra extends PickaxeItem {
    final int maxUseDuration = 72000;

    public PickaxeUltra() {
        super(Tiers.IRON,
                new Item.Properties().attributes(PickaxeItem.createAttributes(Tiers.IRON, 1.0F, -2.8F)));

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        TranslateTool.addItemTranslate(tooltipComponents, ResourceLocation.PICKAXE_ULTRA, ResourceLocation.TranslateKey.HOVER, StyleTool.getDefaultHoverFont());
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

    }

    /**
     * 释放后，移除当前手持物品，抛出一个物品实体
     *
     * @param stack
     * @param level
     * @param livingEntity
     * @param timeCharged
     */
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        Vec3 position = livingEntity.getPosition(1);

        if (!level.isClientSide) {
            // 移除该物品
            InteractionHand usedItemHand = livingEntity.getUsedItemHand();
            livingEntity.setItemInHand(usedItemHand, ItemStack.EMPTY);

            // 丢出物品实体
            CircleItemEntity itemEntity = new CircleItemEntity(level, position.x, position.y, position.z, stack, (Player) livingEntity);
            level.addFreshEntity(itemEntity);

        }
        ParticleUtils.spawnParticles(level, new BlockPos((int) position.x, (int) position.y, (int) position.z), 10, 1, 1, true, ParticleTypes.WAX_ON);
        super.releaseUsing(stack, level, livingEntity, timeCharged);
    }


    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return maxUseDuration;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }


    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

}

```

`\\?\D:\code\projects\neoforge_circlemod\src\main\java\com\circle\circlemod\core\resource\ResourceLocation.java`:

```java
package com.circle.circlemod.core.resource;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * 资源位置
 *
 * @author yuanxin
 * @date 2024/12/12
 */
public enum ResourceLocation {
    GOLD_STAFF("gold_staff", "金法杖"),
    PICKAXE_ULTRA("pickaxe_ultra", "超级稿", new HashMap<>() {
        {
            put(TranslateKey.HOVER.key, "一个挖掘神器");
        }
    }),
    AXOLOTL_SWORD("axolotl_sword", "美西螈剑");

    public final String key;
    public final String name;
    public HashMap<String, String> translateMap = new HashMap<>();

    /**
     * 资源位置
     *
     * @param key  钥匙
     * @param name 名字
     */
    ResourceLocation(String key, String name) {
        this.key = key;
        this.name = name;

    }

    /**
     * 资源位置
     *
     * @param key  钥匙
     * @param name 名字
     */
    ResourceLocation(String key, String name, @Nullable HashMap<String, String> translateMap) {
        this.key = key;
        this.name = name;
        this.translateMap = translateMap;
    }


    /**
     * 查找枚举
     *
     * @param resourceKey 资源键
     * @return {@link ResourceLocation }
     */
    public static ResourceLocation findEnum(ResourceKey resourceKey) {
        return findEnum(resourceKey
                .location()
                .getPath());
    }

    /**
     * 查找枚举
     *
     * @param key 钥匙
     * @return {@link ResourceLocation }
     */
    public static ResourceLocation findEnum(String key) {
        for (ResourceLocation resource : ResourceLocation.values()) {
            if (resource.key.equals(key)) {
                return resource;
            }
        }
        throw new IllegalArgumentException("key is invalid");
    }


    /**
     * 获取拼接后的 translate 键值列表
     * <p>
     * ResourceKey<Item> item.xxx.xxx
     *
     * @return {@link HashMap }<{@link String }, {@link String }>
     */
    public HashMap<String, String> getTranslateKeyValueList(String key) {
        HashMap<String, String> targetTransMap = new HashMap<>();

        translateMap.entrySet()
                    .forEach(translate -> {
                        String path = key + "." + translate.getKey();
                        String content = translate.getValue();
                        targetTransMap.put(path, content);
                    });
        return targetTransMap;
    }

    public String getName(TranslateKey translateKey) {
        return this.translateMap.get(translateKey);
    }

    public enum TranslateKey {
        HOVER("hover");

        public String key;

        TranslateKey(String key) {
            this.key = key;
        }
    }
}
```

`\\?\D:\code\projects\neoforge_circlemod\src\main\java\com\circle\circlemod\datagen\provider\LangProvider.java`:

```java
package com.circle.circlemod.datagen.provider;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.builds.register.CircleUniRegister;
import com.circle.circlemod.core.resource.ResourceLocation;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.HashMap;
import java.util.Map;

public class LangProvider extends LanguageProvider {

    public LangProvider(PackOutput output) {
        super(output, CircleMod.MODID, "zh_cn");
    }

    public void addMapTranslate(Map<String, String> map) {
        map.entrySet()
           .forEach(data -> {
               this.add(data.getKey(), data.getValue());
           });
    }

    @Override
    protected void addTranslations() {
        // 生成物品注册的名字翻译
        CircleUniRegister.ITEMS.getEntries()
                               .forEach(itemDeferredHolder -> {
                                   ResourceKey<Item> key = itemDeferredHolder.getKey();
                                   ResourceLocation enumData = ResourceLocation.findEnum(key);

                                   // 生成物品翻译
                                   String name = enumData.name;
                                   this.add(itemDeferredHolder.get(), name);
                                   // 生成文字翻译
                                   String descriptionId = itemDeferredHolder.get()
                                                                            .getDescriptionId();
                                   HashMap<String, String> translateKeyValueList = enumData.getTranslateKeyValueList(descriptionId);
                                   addMapTranslate(translateKeyValueList);
                               });
    }
}

```

`\\?\D:\code\projects\neoforge_circlemod\src\main\java\com\circle\circlemod\tools\EnchantmentTool.java`:

```java
package com.circle.circlemod.tools;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class EnchantmentTool {

    /**
     * 如果包含则返回附魔等级，否则返回0
     *
     * @param stack       叠
     * @param resourceKey 资源键
     * @return int 包含附魔则返回附魔等级，否则为0
     */
    public static int hasThisEnchantment(ItemStack stack, ResourceKey<Enchantment> resourceKey) {
        int level = 0;

        ItemEnchantments allEnchantments = stack.getAllEnchantments(null);

        for (Holder<Enchantment> enchantment : allEnchantments.keySet()) {
            if (enchantment.is(resourceKey)) {
                level = stack.getEnchantmentLevel(enchantment);
                break;
            }
        }

        return level;
    }
}

```

`\\?\D:\code\projects\neoforge_circlemod\src\main\java\com\circle\circlemod\tools\StyleTool.java`:

```java
package com.circle.circlemod.tools;

import net.minecraft.network.chat.Style;

public class StyleTool {

    /**
     * 获取默认悬停文本
     * 加粗，斜体
     *
     * @return {@link Style }
     */
    public static Style getDefaultHoverFont() {
        return Style.EMPTY.withBold(true)
                          .withItalic(true);

    }

}

```

`\\?\D:\code\projects\neoforge_circlemod\src\main\java\com\circle\circlemod\tools\TranslateTool.java`:

```java
package com.circle.circlemod.tools;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.resource.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;

public class TranslateTool {
    /**
     * 添加物品文字翻译
     *
     * @param components   组件
     * @param location     位置
     * @param TranslateKey translate 键
     * @param style        风格
     */
    public static void addItemTranslate(List<Component> components, ResourceLocation location, ResourceLocation.TranslateKey TranslateKey, Style style) {
        components.add(Component.translatable("item" + "." + CircleMod.MODID + "." + location.key + "." + TranslateKey.key)
                                .withStyle(style));
    }
}

```

`\\?\D:\code\projects\neoforge_circlemod\src\main\resources\assets\circle\models\item\gold_staff.json`:

```json
{
	"credit": "Made with Blockbench",
	"textures": {
		"0": "circle:item/gold_staff",
		"particle": "circle:item/gold_staff"
	},
	"elements": [
		{
			"from": [7, 10.5, 7.5],
			"to": [9, 13, 10],
			"rotation": {"angle": 45, "axis": "x", "origin": [7, 12.25, 7.25]},
			"faces": {
				"north": {"uv": [1, 8, 3, 12], "texture": "#0"},
				"east": {"uv": [4, 0, 8, 4], "texture": "#0"},
				"south": {"uv": [3, 8, 5, 12], "texture": "#0"},
				"west": {"uv": [4, 4, 8, 8], "texture": "#0"},
				"up": {"uv": [7, 12, 5, 8], "texture": "#0"},
				"down": {"uv": [9, 8, 7, 12], "texture": "#0"}
			}
		},
		{
			"from": [7, 8, 5.5],
			"to": [9, 10, 10.5],
			"rotation": {"angle": 0, "axis": "y", "origin": [7, 8, 8.25]},
			"faces": {
				"north": {"uv": [1, 12, 3, 14], "texture": "#0"},
				"east": {"uv": [9, 0, 13, 2], "texture": "#0"},
				"south": {"uv": [3, 12, 5, 14], "texture": "#0"},
				"west": {"uv": [9, 2, 13, 4], "texture": "#0"},
				"up": {"uv": [11, 8, 9, 4], "texture": "#0"},
				"down": {"uv": [11, 8, 9, 12], "texture": "#0"}
			}
		},
		{
			"from": [7, 0, 6.75],
			"to": [9, 2, 9.25],
			"rotation": {"angle": 0, "axis": "y", "origin": [7, 0, 7]},
			"faces": {
				"north": {"uv": [5, 12, 7, 14], "texture": "#0"},
				"east": {"uv": [11, 4, 14, 6], "texture": "#0"},
				"south": {"uv": [7, 12, 9, 14], "texture": "#0"},
				"west": {"uv": [11, 6, 14, 8], "texture": "#0"},
				"up": {"uv": [13, 11, 11, 8], "texture": "#0"},
				"down": {"uv": [13, 11, 11, 14], "texture": "#0"}
			}
		},
		{
			"from": [7.5, 0, 7],
			"to": [8.5, 8, 9],
			"rotation": {"angle": 0, "axis": "y", "origin": [7, 0, 7]},
			"faces": {
				"north": {"uv": [0, 8, 1, 16], "texture": "#0"},
				"east": {"uv": [0, 0, 2, 8], "texture": "#0"},
				"south": {"uv": [8, 0, 9, 8], "texture": "#0"},
				"west": {"uv": [2, 0, 4, 8], "texture": "#0"},
				"up": {"uv": [10, 14, 9, 12], "texture": "#0"},
				"down": {"uv": [11, 12, 10, 14], "texture": "#0"}
			}
		}
	],
	"display": {
		"thirdperson_righthand": {
			"translation": [0, 2.5, 0]
		}
	},
	"groups": [
		{
			"name": "group",
			"origin": [7, 0, 7],
			"color": 0,
			"children": [0, 1, 2, 3]
		}
	]
}
```

`\\?\D:\code\projects\neoforge_circlemod\src\main\resources\assets\circle\models\item\pickaxe_ultra.json`:

```json
{
	"parent": "minecraft:item/iron_pickaxe"
}
```

`\\?\D:\code\projects\neoforge_circlemod\src\main\resources\assets\circle\recipes\pickaxe_ultra.json`:

```json
{
  "type": "minecraft:crafting_shapeless",
  "ingredients": [
    [
      "minecraft:iron_pickaxe"
    ],
    [
      "minecraft:iron_pickaxe"
    ]
  ],
  "result": {
    "id": "circle:pickaxe_ultra",
    "count": 1
  }
}
```

`\\?\D:\code\projects\neoforge_circlemod\src\main\resources\META-INF\accesstransformer.cfg`:

```cfg
# itemEntity 去除bobOffs的final（该属性会造成物品生成后随机产生的位置移动）
public-f net.minecraft.world.entity.item.ItemEntity bobOffs

```

`\\?\D:\code\projects\neoforge_circlemod\src\main\templates\META-INF\neoforge.mods.toml`:

```toml
# This is an example neoforge.mods.toml file. It contains the data relating to the loading mods.
# There are several mandatory fields (#mandatory), and many more that are optional (#optional).
# The overall format is standard TOML format, v0.5.0.
# Note that there are a couple of TOML lists in this file.
# Find more information on toml format here:  https://github.com/toml-lang/toml
# The name of the mod loader type to load - for regular FML @Mod mods it should be javafml
modLoader="javafml" #mandatory

# A version range to match for said mod loader - for regular FML @Mod it will be the FML version. This is currently 2.
loaderVersion="${loader_version_range}" #mandatory

# The license for you mod. This is mandatory metadata and allows for easier comprehension of your redistributive properties.
# Review your options at https://choosealicense.com/. All rights reserved is the default copyright stance, and is thus the default here.
license="${mod_license}"

# A URL to refer people to when problems occur with this mod
#issueTrackerURL="https://change.me.to.your.issue.tracker.example.invalid/" #optional

# A list of mods - how many allowed here is determined by the individual mod loader
[[mods]] #mandatory

# The modid of the mod
modId="${mod_id}" #mandatory

# The version number of the mod
version="${mod_version}" #mandatory

# A display name for the mod
displayName="${mod_name}" #mandatory

# A URL to query for updates for this mod. See the JSON update specification https://docs.neoforged.net/docs/misc/updatechecker/
#updateJSONURL="https://change.me.example.invalid/updates.json" #optional

# A URL for the "homepage" for this mod, displayed in the mod UI
#displayURL="https://change.me.to.your.mods.homepage.example.invalid/" #optional

# A file name (in the root of the mod JAR) containing a logo for display
#logoFile="examplemod.png" #optional

# A text field displayed in the mod UI
#credits="" #optional

# A text field displayed in the mod UI
authors="${mod_authors}" #optional

# The description text for the mod (multi line!) (#mandatory)
description='''${mod_description}'''

# The [[mixins]] block allows you to declare your mixin config to FML so that it gets loaded.
#[[mixins]]
#config="${mod_id}.mixins.json"

# The [[accessTransformers]] block allows you to declare where your AT file is.
# If this block is omitted, a fallback attempt will be made to load an AT from META-INF/accesstransformer.cfg
#[[accessTransformers]]
#file="META-INF/accesstransformer.cfg"

# The coremods config file path is not configurable and is always loaded from META-INF/coremods.json

# A dependency - use the . to indicate dependency for a specific modid. Dependencies are optional.
[[dependencies.${mod_id}]] #optional
    # the modid of the dependency
    modId="neoforge" #mandatory
    # The type of the dependency. Can be one of "required", "optional", "incompatible" or "discouraged" (case insensitive).
    # 'required' requires the mod to exist, 'optional' does not
    # 'incompatible' will prevent the game from loading when the mod exists, and 'discouraged' will show a warning
    type="required" #mandatory
    # Optional field describing why the dependency is required or why it is incompatible
    # reason="..."
    # The version range of the dependency
    versionRange="${neo_version_range}" #mandatory
    # An ordering relationship for the dependency.
    # BEFORE - This mod is loaded BEFORE the dependency
    # AFTER - This mod is loaded AFTER the dependency
    ordering="NONE"
    # Side this dependency is applied on - BOTH, CLIENT, or SERVER
    side="BOTH"

# Here's another dependency
[[dependencies.${mod_id}]]
    modId="minecraft"
    type="required"
    # This version range declares a minimum of the current minecraft version up to but not including the next major version
    versionRange="${minecraft_version_range}"
    ordering="NONE"
    side="BOTH"

# Features are specific properties of the game environment, that you may want to declare you require. This example declares
# that your mod requires GL version 3.2 or higher. Other features will be added. They are side aware so declaring this won't
# stop your mod loading on the server for example.
#[features.${mod_id}]
#openGLVersion="[3.2,)"

```

`\\?\D:\code\projects\neoforge_circlemod\TEMPLATE_LICENSE.txt`:

```txt
MIT License

Copyright (c) 2023 NeoForged project

This license applies to the template files as supplied by github.com/NeoForged/MDK


Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```