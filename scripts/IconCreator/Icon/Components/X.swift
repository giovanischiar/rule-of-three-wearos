//
//  X.swift
//
//
//  Created by Giovani Schiar on 08/12/22.
//

struct X: Tag {
    let x: Double
    let y: Double
    
    let dimensions = Traits.shared.dimensions
    var strokeWidth: Double { dimensions.strokeWidth }
    
    var body: [any Tag] {
        Path()
            .d(XPathData(x: x, y: y, width: dimensions.xSize, height: dimensions.xSize))
            .stroke(color: -"xColor")
            .stroke(width: strokeWidth)
            .fill(color: -"xColor")
    }
}
