package com.example

import io.circe.generic.extras.Configuration

package object circe {

  implicit val circeConfig: Configuration =
    Configuration
      .default
      .withSnakeCaseMemberNames
      .withDefaults

}
