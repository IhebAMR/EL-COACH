//
//  homeViewController.swift
//  El-Coach
//
//  Created by Mac Mini on 12/11/2024.
//

import UIKit
import SwiftUI
class homeViewController: UIViewController,UITableViewDelegate,UITableViewDataSource {
    var exerciceImage=["exercice1","exercice2","exercice3","exercice4","exercice5"]
    var exerciceTime=["3H","5H","2H","1H","2H"]
    var exerciceDiff=["Beginner","Medium","Advanced","Medium","Advanced"]
    var exerciceType=["Cardio","Muscle","Muscle","Cardio","Muscle"]
    var exerciceName=["Pekdeck","PullUp","Rope","Push-Ups","Shoulder Press"]
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        exerciceImage.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "mCell")
        let cv = cell?.contentView
        
        let imageView = cv?.viewWithTag(1) as! UIImageView
        let nameLabel = cv?.viewWithTag(2) as! UILabel
        let diffLabel = cv?.viewWithTag(4) as! UILabel
        let timeLabel = cv?.viewWithTag(3) as! UILabel
        let typeLabel = cv?.viewWithTag(5) as! UILabel
        
        imageView.image = UIImage(named: exerciceImage[indexPath.row])
        nameLabel.text = exerciceName[indexPath.row]
        diffLabel.text = exerciceDiff[indexPath.row]
        timeLabel.text = exerciceTime[indexPath.row]
        typeLabel.text = exerciceType[indexPath.row]
        
        
        return cell!
    }
    
    
    
    
    
    
    @IBOutlet var cardView: UIView!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let arcProgressView = ArcProgressView(frame: CGRect(x: 270, y: 120, width: 100, height: 100))
                arcProgressView.lineWidth = 12
                arcProgressView.arcColor = UIColor.systemGreen
                arcProgressView.backgroundColorArc = UIColor.systemGray5
                arcProgressView.progress = 0.5 // 70% progress
                view.addSubview(arcProgressView)
        
        cardView.layer.cornerRadius = 30
        cardView.clipsToBounds = true
        setupWorkoutSlider()
    }
    func setupWorkoutSlider() {
        // Create the container view
        let sliderView = UIView()
        sliderView.backgroundColor = UIColor.gray
        sliderView.layer.cornerRadius = 20
        sliderView.translatesAutoresizingMaskIntoConstraints = false
        cardView.addSubview(sliderView) // Add slider to cardView

        // Create the label
        let label = UILabel()
        label.text = "Continue the workout"
        label.textColor = UIColor.white
        label.font = UIFont.systemFont(ofSize: 18, weight: .medium)
        label.translatesAutoresizingMaskIntoConstraints = false
        sliderView.addSubview(label)

        // Create the arrow icon (using SF Symbols or an image)
        let arrowImageView = UIImageView()
        arrowImageView.image = UIImage(systemName: "arrow.right.circle.fill")?.withTintColor(.white, renderingMode: .alwaysOriginal)
        arrowImageView.contentMode = .scaleAspectFit
        arrowImageView.translatesAutoresizingMaskIntoConstraints = false
        sliderView.addSubview(arrowImageView)

        // Update constraints to center slider within cardView
        NSLayoutConstraint.activate([
               sliderView.leadingAnchor.constraint(equalTo: cardView.leadingAnchor, constant: 20),
               sliderView.trailingAnchor.constraint(equalTo: cardView.trailingAnchor, constant: -20),
               sliderView.bottomAnchor.constraint(equalTo: cardView.bottomAnchor, constant: -20),
               sliderView.heightAnchor.constraint(equalToConstant: 40),

               label.centerYAnchor.constraint(equalTo: sliderView.centerYAnchor),
               label.leadingAnchor.constraint(equalTo: sliderView.leadingAnchor, constant: 20),

               arrowImageView.centerYAnchor.constraint(equalTo: sliderView.centerYAnchor),
               arrowImageView.trailingAnchor.constraint(equalTo: sliderView.trailingAnchor, constant: -2),
               arrowImageView.widthAnchor.constraint(equalToConstant: 35),
               arrowImageView.heightAnchor.constraint(equalToConstant: 35)
           ])

        // Add swipe gesture (optional for interaction)
        let swipeGesture = UIPanGestureRecognizer(target: self, action: #selector(handleSwipe(_:)))
        sliderView.addGestureRecognizer(swipeGesture)
    }

        
        @objc func handleSwipe(_ gesture: UIPanGestureRecognizer) {
            let translation = gesture.translation(in: view)
            
            // Example behavior - move the view with swipe (customize as desired)
            if let sliderView = gesture.view {
                sliderView.transform = CGAffineTransform(translationX: translation.x, y: 0)
            }
            
            // Reset position on end
            if gesture.state == .ended {
                UIView.animate(withDuration: 0.3) {
                    gesture.view?.transform = .identity
                }
            }
        }
    
 
    
}
