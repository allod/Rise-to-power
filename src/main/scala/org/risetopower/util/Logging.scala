package org.risetopower.util

import org.slf4j.LoggerFactory

trait Logging {
  lazy val logger = LoggerFactory.getLogger(getClass)
}
