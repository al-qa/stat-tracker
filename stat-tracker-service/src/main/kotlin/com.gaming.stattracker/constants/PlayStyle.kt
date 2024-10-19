package com.gaming.stattracker.constants

import com.fasterxml.jackson.annotation.JsonValue

enum class PlayStyle(@JsonValue val style: String) {
    SNIPER("sniper"),
    AGGRESSIVE("aggressive"),
    CAUTIOUS("cautious"),
    ANCHOR("anchor"),
    CAMPER("camper");
    
}
