import UIKit

class ArcProgressView: UIView {
    
    private let backgroundLayer = CAShapeLayer()
    private let progressLayer = CAShapeLayer()
    private let percentageLabel = UILabel()
    
    var lineWidth: CGFloat = 10.0
    var arcColor: UIColor = UIColor.systemBlue
    var backgroundColorArc: UIColor = UIColor.lightGray

    // Progress from 0 to 1
    var progress: CGFloat = 0 {
        didSet {
            updateProgress()
            updateLabel()
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        setupArc()
        setupLabel()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setupArc()
        setupLabel()
    }
    
    private func setupArc() {
        // Define the circular path
        let center = CGPoint(x: bounds.midX, y: bounds.midY)
        let radius = min(bounds.width, bounds.height) / 2 - lineWidth / 2
        let startAngle = CGFloat(-Double.pi / 2)  // 12 o'clock
        let endAngle = CGFloat(3 * Double.pi / 2) // 360 degrees
        
        let circularPath = UIBezierPath(arcCenter: center, radius: radius, startAngle: startAngle, endAngle: endAngle, clockwise: true)

        // Background Layer
        backgroundLayer.path = circularPath.cgPath
        backgroundLayer.strokeColor = backgroundColorArc.cgColor
        backgroundLayer.lineWidth = lineWidth
        backgroundLayer.fillColor = UIColor.clear.cgColor
        layer.addSublayer(backgroundLayer)
        
        // Progress Layer
        progressLayer.path = circularPath.cgPath
        progressLayer.strokeColor = arcColor.cgColor
        progressLayer.lineWidth = lineWidth
        progressLayer.fillColor = UIColor.clear.cgColor
        progressLayer.lineCap = .round
        progressLayer.strokeEnd = 0
        layer.addSublayer(progressLayer)
    }
    
    private func setupLabel() {
        // Set up the label
        percentageLabel.font = UIFont.boldSystemFont(ofSize: 16)
        percentageLabel.textColor = UIColor.black
        percentageLabel.textAlignment = .center
        percentageLabel.frame = CGRect(x: 0, y: 0, width: bounds.width, height: bounds.height)
        percentageLabel.center = CGPoint(x: bounds.midX, y: bounds.midY)
        addSubview(percentageLabel)
        
        updateLabel() // Set initial text
    }
    
    private func updateProgress() {
        progressLayer.strokeEnd = progress
    }
    
    private func updateLabel() {
        let percentage = Int(progress * 100)
        percentageLabel.text = "\(percentage)%"
    }
    
}
