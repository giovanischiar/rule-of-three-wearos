//
//  Dimensions.swift
//
//
//  Created by Giovani Schiar on 13/12/22.
//

class Dimensions {
    let iconSize = 108.0
    var xSize: Double { 10 %% iconSize }
    var questionMarkSize: Double { iconSize * 0.00203704 }
    var hashSize: Double { iconSize * 1/108 }
    var squareSize: Double { 25.0 %% iconSize }
    var strokeWidth: Double { 1.8 %% iconSize }
}
