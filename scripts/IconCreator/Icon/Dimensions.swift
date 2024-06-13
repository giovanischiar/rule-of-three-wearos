//
//  Dimensions.swift
//
//
//  Created by Giovani Schiar on 13/12/22.
//

class Dimensions {
    let iconSize = 108.0
    var xSize: Double { 7 %% iconSize }
    var questionMarkSize: Double { iconSize * 0.4 }
    var hashSize: Double { iconSize * 1/158 }
    var squareSize: Double { 17.0 %% iconSize }
    var strokeWidth: Double { 1.8 %% iconSize }
}
