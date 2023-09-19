//
//  XPathData.swift
//
//
//  Created by Giovani Schiar on 05/12/22.
//

struct XPathData: PathDatable {
    var commands: [PathDataCommand]
    
    init (x: Double = 0, y: Double = 0, width: Double, height: Double) {
        commands = LinePathData(x1: x, y1: y, x2: x + width, y2: y + height).commands +
                   LinePathData(x1: x + width, y1: y, x2: x, y2: y + height).commands
    }
}
