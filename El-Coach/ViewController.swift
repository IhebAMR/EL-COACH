//
//  ViewController.swift
//  El-Coach
//
//  Created by Mac Mini on 12/11/2024.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        let backgroundImage = UIImageView(frame: UIScreen.main.bounds)
           backgroundImage.image = UIImage(named: "background")
           backgroundImage.contentMode = .scaleAspectFill
           
           // Add it to the view and send it to the back
           self.view.addSubview(backgroundImage)
           self.view.sendSubviewToBack(backgroundImage)
    }
   

}

