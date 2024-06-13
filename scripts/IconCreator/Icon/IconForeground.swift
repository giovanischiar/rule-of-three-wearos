//
//  IconForeground.swift
//
//
//  Created by Giovani Schiar on 13/12/22.
//

struct IconForeground: Foregroundable {
    let dimensions = Traits.shared.dimensions
    var iconSize: Double { dimensions.iconSize }
    var squareSize: Double { dimensions.squareSize }
    var strokeWidth: Double { dimensions.strokeWidth }

    var scaleFactor = 1.0
    
    var disposition: Double { (iconSize - squareSize - 75) * scaleFactor }
    
    var foreground: Foreground {
        Foreground(size: iconSize) {
            Div {
                Hash(x: 0, y: 0)
            }
            .position(x: -disposition, y: -disposition)
            .dimensionAlt(width: squareSize, height: squareSize)
            .stroke(color:  -"squareStrokeColor")
            .stroke(width: strokeWidth)
            
            Div {
                Hash(x: 0, y: 0)
            }
            .position(x: disposition, y: -disposition)
            .dimensionAlt(width: squareSize, height: squareSize)
            .stroke(color:  -"squareStrokeColor")
            .stroke(width: strokeWidth)

            X(x: 0, y: 0)

            Div {
                Hash(x: 0, y: 0)
            }
            .position(x: -disposition, y: disposition)
            .dimensionAlt(width: squareSize, height: squareSize)
            .stroke(color:  -"squareStrokeColor")
            .stroke(width: strokeWidth)

            Div {
                QuestionMark(x: 0, y: 0)
            }
            .position(x: disposition, y: disposition)
            .dimensionAlt(width: squareSize, height: squareSize)
            .stroke(color:  -"squareStrokeColor")
            .stroke(width: strokeWidth)
        }
    }
}
