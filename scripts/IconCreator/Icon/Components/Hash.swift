//
//  Hash.swift
//
//
//  Created by Giovani Schiar on 08/12/22.
//

struct Hash: Tag {
    var x: Double
    var y: Double
    
    let dimensions = Traits.shared.dimensions
    var strokeWidth: Double { dimensions.strokeWidth }
    
    var body: [any Tag] {
        Path()
            .d(HashPathData(x: x, y: y))
            .stroke(color: -"hashColor")
            .stroke(width: strokeWidth)
            .fill(opacity: 0.0)
            .scaled(factor: dimensions.hashSize)
    }
}
