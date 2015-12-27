package com.catinthedark.gban.view

sealed trait State
case object UP extends State
case object DOWN extends State
case object RUNNING extends State
case object CRAWLING extends State
case object SHOOTING extends State
case object KILLED extends State