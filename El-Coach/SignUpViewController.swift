//
//  SignUpViewController.swift
//  El-Coach
//
//  Created by Mac Mini on 12/11/2024.
//

import UIKit

class SignUpViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor(named: "#001C2F")
        
        [fullname, email, password, confirmpass].forEach { styleTextField($0) }
        
        let backgroundImage = UIImageView(frame: UIScreen.main.bounds)
           backgroundImage.image = UIImage(named: "background")
           backgroundImage.contentMode = .scaleAspectFill
           
           // Add it to the view and send it to the back
           self.view.addSubview(backgroundImage)
           self.view.sendSubviewToBack(backgroundImage)


        // Do any additional setup after loading the view.
    }
    
    func styleTextField(_ textField: UITextField) {
        textField.backgroundColor = UIColor(named: "#FFF2DC")
        textField.layer.cornerRadius = 8
        textField.layer.borderWidth = 1
        textField.layer.borderColor = UIColor(named: "#B1BAC7")?.cgColor
        textField.textColor = UIColor(named: "#001C2F")
        textField.font = UIFont.systemFont(ofSize: 16)
    }
    

    
    
    
    @IBOutlet var fullname: UITextField!
    
    @IBOutlet var email: UITextField!
    
    
    
    @IBOutlet var password: UITextField!
    
    
    
    @IBOutlet var confirmpass: UITextField!
    
    @IBAction func login(_ sender: UIButton) {
        sender.backgroundColor = UIColor(named: "#FFC9A3")
        sender.setTitleColor(UIColor(named: "#001C2F"), for: .normal)
           sender.layer.cornerRadius = 8
           sender.titleLabel?.font = UIFont.boldSystemFont(ofSize: 18)
    }
    
    
    
}
