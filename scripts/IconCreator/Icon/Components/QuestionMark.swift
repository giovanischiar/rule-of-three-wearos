//
//  QuestionMark.swift
//
//
//  Created by Giovani Schiar on 08/12/22.
//

struct QuestionMark: Tag {
    var x: Double
    var y: Double
    
    let dimensions = Traits.shared.dimensions
    var size: Double { dimensions.iconSize }
        
    var body: [any Tag] {
        Path()
            .d(QuestionMarkPathData(x: x, y: y))
            .stroke(color: -"questionMarkColor")
            .stroke(width: 1)
            .fill(color: -"questionMarkColor")
            .scaled(factor: size * 0.00203704)
    }
}
