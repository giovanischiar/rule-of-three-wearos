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
    
    var disposition: Double { (iconSize - squareSize - 60) * scaleFactor }
    
    var foreground: Foreground {
        Foreground(size: iconSize) {
            Div {
                Hash(x: disposition, y: disposition)
            }
            .position(x: -disposition, y: -disposition)
            .dimension(width: squareSize, height: squareSize)
            .stroke(color:  -"squareStrokeColor")
            .stroke(width: strokeWidth)
            
            Div {
                Hash(x: -disposition, y: disposition)
            }
            .position(x: disposition, y: -disposition)
            .dimension(width: squareSize, height: squareSize)
            .stroke(color:  -"squareStrokeColor")
            .stroke(width: strokeWidth)

            X(x: 0, y: 0)

            Div {
                Hash(x: disposition, y: -disposition)
            }
            .position(x: -disposition, y: disposition)
            .dimension(width: squareSize, height: squareSize)
            .stroke(color:  -"squareStrokeColor")
            .stroke(width: strokeWidth)

            Div {
                QuestionMark(x: -disposition, y: -disposition)
            }
            .position(x: disposition, y: disposition)
            .dimension(width: squareSize, height: squareSize)
            .stroke(color:  -"squareStrokeColor")
            .stroke(width: strokeWidth)
        }
    }
}
