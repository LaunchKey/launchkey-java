# LaunchKey Java SDK - CLI Example


  * [Pre-Requisites](#prerequisites)
  * [Installation](#installation)
  * [Usage](#usage)

# <a name="prerequisites"></a>Pre-Requisites

Follow the pre-requisites instructions for the LaunchKey SDK: [SDK Instructions](../../sdk/README.md#prerequisites).
The demo will utilize the Bouncy Castle JCE provider.  Make sure to install that provider in the instructions.

# <a name="installation"></a>Installation

Execute the Maven goal of _package_. Executing _clean_ as well is never a bad idea.  For example:

```
mvn clean package
```

#  <a name="usage"></a>Usage

  1. Help
  
    Help can be obtained by either executing the application without any parameters or with -h
    
    ```
    java -jar launchkey-java-cli-demo-1.0.0.jar -h
    ```

  2. Ping
   
    Ping can be used to verify that you have properly installed the cryptography requirements.
     
    Example:

    ```
    java -jar launchkey-java-cli-demo-1.0.0.jar 123456789 abcdefghijklmnopqrst /tmp/private.key ping
    ```
    
    After executing you should see a response similar to this:
    
    ```
    Attempting ping request for app key 4152794039
    Ping request successful
        LaunchKey Time: 2015-02-04 18:12:52
        Public Key: -----BEGIN PUBLIC KEY-----
    MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8zQos4iDSjmUVrFUAg5G
    uhU6GehNKb8MCXFadRWiyLGjtbGZAk8fusQU0Uj9E3o0mne0SYESACkhyK+3M1Er
    bHlwYJHN0PZHtpaPWqsRmNzui8PvPmhm9QduF4KBFsWu1sBw0ibBYsLrua67F/wK
    PaagZRnUgrbRUhQuYt+53kQNH9nLkwG2aMVPxhxcLJYPzQCat6VjhHOX0bgiNt1i
    HRHU2phxBcquOW2HpGSWcpzlYgFEhPPQFAxoDUBYZI3lfRj49gBhGQi32qQ1YiWp
    aFxOB8GA0Ny5SfI67u6w9Nz9Z9cBhcZBfJKdq5uRWjZWslHjBN3emTAKBpAUPNET
    nwIDAQAB
    -----END PUBLIC KEY-----
    ```

  3. Login
  
    Performing a login request is accomplished with the login command which is passed a username.
     
    Example: Execute a login request for user _myusername_.
     
    ```
    java -jar launchkey-java-cli-demo-1.0.0.jar 123456789 abcdefghijklmnopqrst /tmp/private.key login myusername
    ```
    
    The request will happen in two steps.  The application will make an authorization request and then poll for a user
    response:  That portion will look something like this:

    ```
    Login request successful
        LaunchKey Time: 2015-02-04 18:29:27
        Auth Request: wb9phc6kv2qg5ajplr13tpq8b5sx4ukn
    Checking for response from user
    ..............
    ```
    
    One of three scenarios will occur.  The request will timeout or the user will accept or reject the request.  Here are
    examples of each:

    Request timed out:
    
    ```
    Login request timed out
    ```

    User accepted:

    ```
    Login request accepted by user
        App PINs:     3722,861,8293,2115,2255
        Auth Request: wb9phc6kv2qg5ajplr13tpq8b5sx4ukn
        Device ID:    e0SE
        user Hash:    B8luyaSwKdFnABYghEZdPLb3QI7RJ02yfYnAr67EPpi
        User Push ID: null
    ```

    User rejected:

    ```
    Login request rejected by user
    ```

  4. Verify user is still authorized (has not de-orbited)

    Pass the _Auth Request_ value from the login request to the authorized command

    ```
    java -jar launchkey-java-cli-demo-2.0.0.jar 123456789 abcdefghijklmnopqrst /tmp/private.key authorized wb9phc6kv2qg5ajplr13tpq8b5sx4ukn
    ```
    
    One of two scenarios will occur, authorized or unauthorized.  Here are examples of each:
    
    ```
    User is still authorized
    ```
    
    ```
    User is not authorized
    ```

  5. Log out a user
  
    Pass the _Auth Request_ value from the login request to the logout command
    
    ```
    java -jar launchkey-java-cli-demo-2.0.0.jar 123456789 abcdefghijklmnopqrst /tmp/private.key logout wb9phc6kv2qg5ajplr13tpq8b5sx4ukn
    ```
    
    You should receive the following response:
    
    ```
    User is logged out.
    ```
    
  6. Pair a white label user
  
    White label apps are applications that use the LaunchKey WhiteLabel SDK to embed the functionality of the
    LaunchKey Mobile Authenticator.  A separate process for pairing users and devices in the LaunchKey system is needed
    for these users within a White Label Group.  __This command requires that the Rocket Key used in the command to
    belong to a White Label Group.  If you are not using a White Label Rocket, the command will error.__
    
    Pass a unique identifier for a user in your system to the white-label-pair-user command.  The example request
    below uses a GUID.
    
    Request:
    
    ```
    java -jar launchkey-java-cli-demo-2.0.0.jar 123456789 abcdefghijklmnopqrst /tmp/private.key white-label-pair-user 326335b0-8569-4aa3-90a3-ac4372104ea3
    ```

    Response when the Rocket Key is not for a White Label Rocket:
    
    ```
    There was an error creating your user: Unable to find app
    ```
    
    Response when White Label Group User is successfully created:
    
    ```
    White label user creation request successful
        QR Code URL: https://dashboard.launchkey.com/qrcode/5r53j9z
        LaunchKey Identifier: 6IsYb4D5KiL8KJCQgBEkEjhOQaxsHtPNRDLEZjJjZLw
        Manual verification code: 5r53j9z
    ```
     