# Beenpwned4s
Scala client for the "Have I Been Pwned API v2" https://haveibeenpwned.com/API/v2

## Install

## Example

Find if an email is Pwned:
```scala
import io.onema.beenpwned4s.PwnedClient

val client = PwnedClient()
if(client.isEmailBreached("foo@bar.com")) {
  println("Email is pwned")
} else {
  println("Email is not pwned")
}

val emailResults: Option[Seq[PwnedClient.Breach]] = client.getEmailBreached("foo@bar.com")
emailResults.isDefined
println(s"The email has been pwned ${emailResults.get.length} times")

```

Find if the password is Pwned:

```scala
import io.onema.beenpwned4s.PwnedClient

val client = PwnedClient()
if(client.isPasswordPwned("password")) {
  println("Password is pwned")
} else {
  println("Password is not pwned")
}

val passwordResult: Option[PwnedClient.HashPwned] = client.getPasswordPwned("password")
passwordResult.isDefined
passwordResult.foreach(p => {
  println(s"The password-hash suffix ${p.hashSuffix} has been pwned ${p.count} times")
})

```