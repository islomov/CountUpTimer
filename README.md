# CountUpTimer
Simple CountUpTimer in Kotlin


![](https://github.com/islomov/CountUpTimer/blob/master/screenshots/timerup.gif)

## Installation using Gradle

`implementation 'me.dara.avatardrawable:avatardrawable:1.0.0'`

## Usage

```
val timer = object : CountUpTimer(100){
   override fun onTick(elapsedTime: Long) {
      textView.text = elapsedTime.toString()
   } 
}
timer.start()
```

## Notice

You can use this class with any thread you want.</br>
CountUpTimer's Looper is associated with thread which calls method start().</br>
That means you can create instance of CountUpTimer in any thread, but you have to know where to call start() method

```
val thread = Thread(Runnable { 
   run { 
      val timer = object : CountUpTimer(100){
         override fun onTick(elapsedTime: Long) {
            textView.text = elapsedTime.toString()
         }
    
       }
       timer.start()
      }
    })
thread.start()
```


## License
### Apache-2.0

```
Copyright 2018 Sardor Islomov
Licensed under the Apache License, Version 2.0 (the "License");</br>
you may not use this file except in compliance with the<br/> License. </br>
You may obtain a copy of the License at</br>

  http://www.apache.org/licenses/LICENSE-2.0<br/>

Unless required by applicable law or agreed to in writing, software</br>
distributed under the License is distributed on an "AS IS" BASIS</br>
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.</br>
See the License for the specific<br/>language governing permissions and</br>
limitations under the License.
```
