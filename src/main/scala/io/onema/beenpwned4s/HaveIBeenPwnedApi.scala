/**
  * This file is part of the ONEMA beenpwned4s Package.
  * For the full copyright and license information,
  * please view the LICENSE file that was distributed
  * with this source code.
  *
  * copyright (c) 2019, Juan Manuel Torres (http://onema.io)
  *
  * @author Juan Manuel Torres <software@onema.io>
  */

package io.onema.beenpwned4s

import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import requests.Response


trait HaveIBeenPwnedApi {

  //--- Methods ---
  def pwnedPassword(hashedPassword: String): Response

  def breachedEmail(email: String): Response

  def breaches(siteName: Option[String] = None): Response

  def pastedEmail(email: String): Response
}

object HaveIBeenPwnedApi {
  
  //--- Fields ---
  val version: String = "api/v2"
  val emailUrl = "https://haveibeenpwned.com"
  val passwordUrl = "https://api.pwnedpasswords.com"
  val userAgent: String = "beenpawned4s"

  //--- Extension Methods ---
  implicit class stringExtensions(str: String) {
    def urlEncoded: String = URLEncoder.encode(str.trim, StandardCharsets.UTF_8.toString).toLowerCase
    def sha1: String = {
      val md = java.security.MessageDigest.getInstance("SHA-1")
      md.digest(str.getBytes("UTF-8")).map("%02x".format(_)).mkString
    }
  }
}
