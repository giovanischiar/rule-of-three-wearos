//
//  HashPathData.swift
//  
//
//  Created by Giovani Schiar on 05/12/22.
//

struct HashPathData: PathDatable {
    var commands: [PathDataCommand]
    
    init(x: Double, y: Double) {
        let firstLine = LinePathData(x1: x + 5.2 + 1.6, y1: y, x2: x + 1.6, y2: y + 19.2)
        let secondLine = LinePathData(x1: x + 1.6, y1: y + 6.7, x2: x + 11.4 + 1.6, y2: y + 6.7)
        commands = (
            firstLine +
            firstLine.moved(x: 4.9, y: 0) +
            secondLine +
            secondLine.moved(x: -1.6, y: 5.9)
        ).commands
    }
}

