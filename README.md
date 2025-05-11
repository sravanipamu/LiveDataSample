# Custom LiveData Library

This module is a **custom version of Android's LiveData**. It behaves just like the real LiveData used in Android apps.

- It helps you **store and manage data** that can change over time.
- It is **lifecycle-aware**, which means it only updates the UI when the screen (activity or fragment) is in a visible or active state.
- When the data changes, it **automatically notifies the UI**, so you don’t need to update the screen manually.

## Library Import

To use this custom `LiveData` library in your project, add the following to your `build.gradle`:

implementation(project(":livedata"))

Here’s an example of how to create and use the custom LiveData in your project:

```kotlin
//  Create a MutableLiveData instance
val liveData = MutableLiveData<String>()

// Observe the data (lifecycle-aware)
liveData.observe(this, object : Observer<String> {
    override fun onChanged(value: String) {
        simpleTextView.setText(value)
    }
})

// Set the data
liveData.setValue("Hello lifecycle-aware LiveData!")
