## SafeFragmentTransaction
I'm sure that every android developer has faced exception

```java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState``` 

after ```fragmentTransaction.commit()``` or ```fragmentManager.popBackStack(...)``` when activity is paused. To avoid this, use ```SafeFragmentTransaction```. If activity is paused and fragment transactions are requested, ```SafeFragmentTransaction``` ensures that these transactions will be executed after activity comes alive. So there are no crashes and life is beautiful. 
It's very very easy to use:

## Gradle:
Add it in your root build.gradle at the end of repositories:
```Groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Add dependency to app gradle:
```Groovy
implementation 'com.github.Zuluft:SafeFragmentTransaction:v1.0'
```

## Step 1:

Simply extend your activity by ```SafeFragmentTransactorActivity```

or

Write the following in your base activity:
```Java
...
private SafeFragmentTransaction mSafeFragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSafeFragmentTransaction = SafeFragmentTransaction.createInstance(getLifecycle(),
                getSupportFragmentManager());
        getLifecycle().addObserver(mSafeFragmentTransaction);
    }

    public final SafeFragmentTransaction getSafeFragmentTransaction() {
        return mSafeFragmentTransaction;
    }
...
```

## Step 2:

When adding, replacing or poping fragments, write the following:
```Java
...
getSafeFragmentTransaction().registerFragmentTransition(fragmentManager ->
                    fragmentManager.popBackStackImmediate()
                    fragmentManager.beginTransaction()
                            .add(R.id.content1, new Fragment1(), "TAG1")
                            .replace(R.id.content2, new Fragment2(), "TAG2")
                            .commit());
...
```

You can use ```SafeFragmentTransaction``` for child fragments too. Write the following in ```onCreateView(...)``` of your fragment:

 ```Java
mSafeFragmentTransaction = SafeFragmentTransaction.createInstance(getLifecycle(),
                getChildFragmentManager());
```
And use it as it is shown in  ```step 2```.
